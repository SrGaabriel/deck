package io.github.deck.gateway

import io.github.deck.common.util.Constants
import io.github.deck.gateway.event.*
import io.github.deck.gateway.event.type.GatewayHelloEvent
import io.github.deck.gateway.util.EventSupplier
import io.github.deck.gateway.util.EventSupplierData
import io.github.deck.gateway.util.await
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.*
import mu.KLoggable
import mu.KLogger

/**
 * Base gateway interface
 */
public interface Gateway : EventSupplier {
    public val gatewayId: Int
    public val scope: CoroutineScope
    public val hello: GatewayHelloEvent
    public val eventSharedFlow: SharedFlow<GatewayEvent>
    public val webSocketSession: DefaultWebSocketSession

    public suspend fun connect()

    public suspend fun startHeartbeat(): Job

    public suspend fun startListening(): Job

    public suspend fun sendCommand(command: GatewayCommand)

    public suspend fun disconnect(expectingReconnect: Boolean = false)
}

/**
 * Default implementation of [Gateway]
 */
public class DefaultGateway(
    private val token: String,
    private val debugPayloads: Boolean,
    override val gatewayId: Int,
    override val scope: CoroutineScope,
    override val eventSharedFlow: MutableSharedFlow<GatewayEvent>,
    private val client: HttpClient,
    private val eventDecoder: EventDecoder = DefaultEventDecoder(gatewayId),
    private val commandEncoder: CommandEncoder = DefaultCommandEncoder()
) : Gateway, KLoggable {
    override lateinit var hello: GatewayHelloEvent
    override lateinit var webSocketSession: DefaultWebSocketSession

    private lateinit var listeningJob: Job
    private lateinit var heartbeatJob: Job

    override val logger: KLogger = logger("Gateway $gatewayId Logger")

    override val eventSupplierData: EventSupplierData by lazy {
        EventSupplierData(
            scope = scope,
            sharedFlow = eventSharedFlow
        )
    }

    /**
     * Simply creates the websocket session.
     *
     * @see [startHeartbeat]
     * @see [startListening]
     */
    override suspend fun connect() {
        webSocketSession = client.webSocketSession(host = Constants.GuildedGateway, path = Constants.GuildedGatewayPath) {
            header(HttpHeaders.Authorization, "Bearer $token")
        }
    }

    /**
     * Starts collecting payloads from gateway, and ignores them
     * if they're Pong (response to heartbeats) payloads.
     */
    override suspend fun startListening(): Job = scope.launch {
        webSocketSession.incoming.receiveAsFlow().filterIsInstance<Frame.Text>().collect { frame ->
            if (frame.data.contentEquals(Constants.GatewayPongContent.toByteArray())) return@collect
            val data = eventDecoder.decodeDataFromPayload(frame.readText())
            val event = eventDecoder.decodeEventFromPayload(data)
                ?: return@collect logger.info { "[DECK Gateway #${gatewayId}] Failed to parse event with body ${data.data}" }
            logger.info { "[DECK Gateway #${gatewayId}] Received event ${data.type}".let { log -> if (debugPayloads) "$log with JSON ${data.data}" else log } }
            eventSharedFlow.emit(event)
        }
    }.also { listeningJob = it }

    /**
     * Uses a ticker channel to schedule heartbeats/pings. Needs to be called
     * after [startListening] so it can handle the [GatewayHelloEvent] event.
     */
    @OptIn(ObsoleteCoroutinesApi::class)
    override suspend fun startHeartbeat(): Job = scope.launch {
        hello = await(8000, gatewayId = gatewayId) ?: return@launch logger.error { "Gateway hello payload wasn't sent in time in gateway $gatewayId." }
        val tickerChannel = ticker(hello.heartbeatIntervalMs, 5000)
        logger.info { "[DECK Gateway #${gatewayId}] Created ticker channel, now starting to send heartbeats to guilded." }
        tickerChannel.consumeAsFlow().collect {
            webSocketSession.send(Constants.GatewayPingContent)
        }
    }.also { heartbeatJob = it }

    override suspend fun sendCommand(command: GatewayCommand): Unit = scope.launch {
        val encoded = commandEncoder.encodeCommandToString(command)
        webSocketSession.send(Frame.Text(encoded))
        logger.info { "[DECK Gateway #${gatewayId}] Sent command $encoded" }
    }.let {}

    override suspend fun disconnect(expectingReconnect: Boolean) {
        val code = if (expectingReconnect) CloseReason.Codes.SERVICE_RESTART else CloseReason.Codes.GOING_AWAY
        heartbeatJob.cancel("Shutdown")
        listeningJob.cancel("Shutdown")
        logger.info { "[DECK Gateway #${gatewayId}] Disconnecting..." }
        webSocketSession.close(reason = CloseReason(code, "Shutdown"))
    }
}

/**
 * Instead of starting listening and sending heartbeats in [Gateway.connect],
 * we assign another function to bear this responsibility, since we don't want it handling multiple tasks.
 *
 * Calls [Gateway.connect], [Gateway.startListening], [Gateway.startHeartbeat] in order.
 */
public suspend fun Gateway.start() {
    connect()
    startListening()
    startHeartbeat()
}