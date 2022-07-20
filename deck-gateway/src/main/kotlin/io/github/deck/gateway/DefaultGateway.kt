package io.github.deck.gateway

import io.github.deck.common.log.error
import io.github.deck.common.log.info
import io.github.deck.common.util.Constants
import io.github.deck.gateway.event.DefaultEventDecoder
import io.github.deck.gateway.event.EventDecoder
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayHelloEvent
import io.github.deck.gateway.util.await
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.MutableSharedFlow

public class DefaultGateway(
    override val id: Int,
    public val orchestrator: GatewayOrchestrator,
) : Gateway {
    private var hello: GatewayHelloEvent? = null
    private var webSocketSession: DefaultWebSocketSession? = null

    override val scope: CoroutineScope = orchestrator
    override val eventFlow: MutableSharedFlow<GatewayEvent> by orchestrator::globalEventsFlow

    override var lastMessageId: String? = null

    private var listeningJob: Job? = null
    private var heartbeatJob: Job? = null

    public var eventDecoder: EventDecoder = DefaultEventDecoder(id)
    private val logger by orchestrator::logger

    override var state: GatewayState = GatewayState.Closed

    override suspend fun connect() {
        webSocketSession = orchestrator.httpClient.webSocketSession(host = Constants.GuildedGateway, path = Constants.GuildedGatewayPath) {
            header(HttpHeaders.Authorization, "Bearer ${orchestrator.token}")
            if (orchestrator.enableEventReplaying && lastMessageId != null) {
                state = GatewayState.Replaying
                header("guilded-last-message-id", lastMessageId)
            } else {
                state = GatewayState.Active
            }
        }
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    override suspend fun startPinging(): Job {
        heartbeatJob = orchestrator.launch {
            hello = await(8000)
                ?: return@launch logger.error { "[Gateway $id] Hello payload wasn't sent in time." }
            val ticker = ticker(hello!!.heartbeatIntervalMs, 5000)
            logger.info { "[Gateway $id] Created ticker channel, now starting to send heartbeats." }
            ticker.consumeEach {
                webSocketSession?.send(Constants.GatewayPingContent)
            }
        }
        return heartbeatJob!!
    }

    override suspend fun startListening(): Job {
        listeningJob = scope.launch(Dispatchers.Default) {
            while (state.listening) {
                val frame = webSocketSession?.incoming?.receive()
                    ?: error("Websocket session was rendered null")
                if (frame !is Frame.Text || frame.data.contentEquals(Constants.GatewayPongContent.toByteArray()))
                    continue

                val text = frame.readText()
                val payload = eventDecoder.decodePayloadFromData(text)
                val event = eventDecoder.decodeEventFromPayload(payload)

                if (event == null) {
                    logger.error { "[Gateway $id] Failed to parse event with data $text" }
                    continue
                }

                logger.info { "[Gateway $id] Received event ${payload.type}".let { log -> if (orchestrator.logEventPayloads) "$log from payload $text" else log } }
                lastMessageId = event.payload.messageId
                eventFlow.emit(event)
            }
        }
        return listeningJob!!
    }

    override suspend fun disconnect(expectingReconnect: Boolean) {
        state = GatewayState.Closed
        val code = if (expectingReconnect) CloseReason.Codes.SERVICE_RESTART else CloseReason.Codes.GOING_AWAY
        heartbeatJob?.cancel("Shutdown")
        listeningJob?.cancel("Shutdown")
        logger.info { "[Gateway $id] Disconnecting" }
        webSocketSession?.close(reason = CloseReason(code, "Shutdown"))
    }
}