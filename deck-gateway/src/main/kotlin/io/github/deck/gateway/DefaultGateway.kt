package io.github.deck.gateway

import io.github.deck.common.log.debug
import io.github.deck.common.log.error
import io.github.deck.common.log.info
import io.github.deck.common.util.Constants
import io.github.deck.gateway.event.DefaultEventDecoder
import io.github.deck.gateway.event.EventDecoder
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayHelloEvent
import io.github.deck.gateway.util.GatewayConstants
import io.github.deck.gateway.util.await
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.TickerMode
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.atomic.AtomicInteger

public class DefaultGateway(
    override val id: Int,
    public val orchestrator: GatewayOrchestrator,
) : Gateway {
    public var websocketSession: ClientWebSocketSession? = null

    public var listeningJob: Job? = null
    public var heartbeatJob: Job? = null

    override var lastMessageId: String? = null
    private var hasSentAnythingAfterPing: Boolean = true

    override val state: MutableStateFlow<GatewayState> = MutableStateFlow(GatewayState.Closed)
    override val scope: CoroutineScope = orchestrator
    override val eventFlow: MutableSharedFlow<GatewayEvent> by orchestrator::globalEventsFlow

    override var maxRetries: Int = 5
    override val retryCounter: AtomicInteger = AtomicInteger(0)
    public var eventDecoder: EventDecoder = DefaultEventDecoder(id)

    override suspend fun start() {
        connect()
        startListening()
        startPinging()
        startCheckingForRetry()
    }

    override suspend fun connect() {
        websocketSession = orchestrator.httpClient.webSocketSession(host = Constants.GuildedGateway, path = Constants.GuildedGatewayPath) {
            header(HttpHeaders.Authorization, "Bearer ${orchestrator.token}")
            if (lastMessageId != null)
                header("guilded-last-message-id", lastMessageId)
        }
        state.emit(if (lastMessageId == null) GatewayState.Active else GatewayState.Replaying)
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    override suspend fun startPinging(): Job {
        heartbeatJob = scope.launch {
            val hello = this@DefaultGateway.await<GatewayHelloEvent>(10_000)
                ?: return@launch orchestrator.logger.error { "Hello event was not sent in time in gateway $id." }
            orchestrator.logger.info { "[Gateway $id] Now starting to send heartbeats to Guilded" }
            val ticker = ticker(
                delayMillis = hello.heartbeatIntervalMs,
                mode = TickerMode.FIXED_PERIOD
            )
            ticker.consumeEach {
                if (state.value != GatewayState.Active)
                    return@consumeEach
                if (!hasSentAnythingAfterPing && retryCounter.get() < maxRetries) {
                    orchestrator.logger.error { "[Gateway $id] Gateway has not heard of Guilded for a while. Trying to reconnect... [${retryCounter.get()+1}/$maxRetries]" }
                    state.emit(GatewayState.Retrying)
                    return@consumeEach
                }
                websocketSession?.send(GatewayConstants.GatewayPing)
//                hasSentAnythingAfterPing = false
            }
        }
        return heartbeatJob!!
    }

    override suspend fun startListening(): Job {
        listeningJob = scope.launch {
            while (state.value == GatewayState.Active) {
                val frame = websocketSession?.incoming?.receive()
                    ?: error("Error trying to receive new frame")
                hasSentAnythingAfterPing = true
                if (frame is Frame.Close) {
                    val reason = frame.readReason()
                    orchestrator.logger.error { "[Gateway $id] Received close frame with reason (${reason?.knownReason}: ${reason?.message})]" }
                    shutdown()
                    continue
                }
                if (frame !is Frame.Text || frame.data.contentEquals(GatewayConstants.GatewayPong.toByteArray()))
                    continue

                val payload = frame.readText()
                val event = eventDecoder.decodeEventFromPayload(payload)

                if (event == null) {
                    orchestrator.logger.error { "[Gateway $id] Failed to parse event with data $payload" }
                    continue
                }

                orchestrator.logger.debug { "[Gateway $id] Received event ${event.info.type}".let { log -> if (orchestrator.logEventPayloads) "$log from payload $payload" else log } }
                lastMessageId = event.info.lastMessageId
                eventFlow.emit(event)
            }
        }
        return listeningJob!!
    }

    private fun startCheckingForRetry() {
        scope.launch {
            state.collect {
                if (it != GatewayState.Retrying || retryCounter.get() >= maxRetries)
                    return@collect
                retryCounter.incrementAndGet()
                retry()
            }
        }
    }

    private suspend fun retry() {
        disconnect(true)
        start()
    }

    override suspend fun disconnect(expectingReconnect: Boolean) {
        val code = if (expectingReconnect) CloseReason.Codes.SERVICE_RESTART else CloseReason.Codes.GOING_AWAY
        orchestrator.logger.debug { "[Gateway $id] Disconnecting with code ${code.name}" }
        shutdown()
        websocketSession?.close(CloseReason(code, "Shutdown"))
        orchestrator.logger.info { "[Gateway $id] Disconnected successfully" }
    }

    private fun shutdown() {
        heartbeatJob?.cancel("Shutdown")
        listeningJob?.cancel("Shutdown")
    }
}