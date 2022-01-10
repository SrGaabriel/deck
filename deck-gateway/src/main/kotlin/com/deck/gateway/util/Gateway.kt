package com.deck.gateway.util

import com.deck.common.util.*
import com.deck.gateway.GatewayOrchestrator
import com.deck.gateway.event.DefaultEventDecoder
import com.deck.gateway.event.EventDecoder
import com.deck.gateway.event.GatewayEvent
import com.deck.gateway.event.GatewayHelloEvent
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.*
import mu.KLoggable
import mu.KLogger

/**
 * Base gateway interface
 */
interface Gateway {
    val gatewayId: Int
    val scope: CoroutineScope
    val hello: GatewayHelloEvent
    val parameters: GatewayParameters
    val eventSharedFlow: SharedFlow<GatewayEvent>
    val webSocketSession: DefaultWebSocketSession

    suspend fun connect()

    suspend fun startHeartbeat(): Job

    suspend fun startListening(): Job

    suspend fun disconnect(expectingReconnect: Boolean = false)
}

/**
 * All parameters required by guilded to connect
 * to the gateway.
 *
 * @param jwt unknown
 * @param eioVersion EngineIO version
 * @param transport transport method
 * @param guildedClientId client id
 * @param teamId **optional** id of the team you'll connect your gateway to
 */
data class GatewayParameters(
    @DeckUnknown val jwt: String = "undefined",
    val eioVersion: Int = 3,
    val transport: String = "websocket",
    val guildedClientId: String,
    val teamId: GenericId? = null
) {
    fun buildPath() = "socket.io/?jwt=$jwt&EIO=$eioVersion&transport=$transport&guildedClientId=$guildedClientId".let {
        if (teamId != null) "$it&teamId=$teamId" else it
    }
}

/**
 * Default implementation of [Gateway]
 */
class DefaultGateway(
    override val gatewayId: Int,
    override val scope: CoroutineScope,
    override val parameters: GatewayParameters,
    override val eventSharedFlow: MutableSharedFlow<GatewayEvent>,
    private val client: HttpClient,
    private val eventDecoder: EventDecoder = DefaultEventDecoder(gatewayId)
): Gateway, KLoggable {
    override lateinit var hello: GatewayHelloEvent
    override lateinit var webSocketSession: DefaultWebSocketSession

    private lateinit var listeningJob: Job
    private lateinit var heartbeatJob: Job

    override val logger: KLogger = logger("Gateway $gatewayId Logger")

    /**
     * Simply creates the websocket session with the specified
     * [parameters], nothing more nothing less.
     *
     * @see [startHeartbeat]
     * @see [startListening]
     */
    override suspend fun connect() {
        webSocketSession = client.webSocketSession(host = Constants.GuildedGateway, path = parameters.buildPath())
    }

    /**
     * Starts collecting payloads from gateway, and ignores them
     * if they're Pong (response to heartbeats) payloads.
     */
    override suspend fun startListening(): Job = scope.launch {
        webSocketSession.incoming.receiveAsFlow().filterIsInstance<Frame.Text>().collect {
            if (it.data.contentEquals(Constants.GatewayPongContent.toByteArray())) return@collect
            val payload = eventDecoder.decodePayloadFromString(it.readText()) ?: return@collect
            val event = eventDecoder.decodeEventFromPayload(payload) ?: return@collect
            logger.info { "[DECK Gateway #${gatewayId}] Received $event" }
            eventSharedFlow.emit(event)
        }
    }.also { listeningJob = it }

    /**
     * Uses a ticker channel to schedule heartbeats/pings. Needs to be called
     * after [startListening] so it can handle the [GatewayHelloEvent] event.
     */
    @OptIn(ObsoleteCoroutinesApi::class)
    override suspend fun startHeartbeat(): Job = scope.launch {
        val helloPayload = await<GatewayHelloEvent>(8000) ?: error("Gateway hello payload wasn't sent in time in gateway $gatewayId.")
        hello = helloPayload
        val tickerChannel = ticker(helloPayload.pingInterval, helloPayload.pingTimeout)
        logger.info { "[DECK Gateway #${gatewayId}] Created ticker channel, now starting to send heartbeats to guilded." }
        tickerChannel.consumeAsFlow().collect {
            webSocketSession.send(Constants.GatewayPingContent)
        }
    }.also { heartbeatJob = it }

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
suspend fun Gateway.start() {
    connect()
    startListening()
    startHeartbeat()
}

/**
 * Dispatches an event, to specify the gateway you want to dispatch
 * your event, change the [GatewayEvent.gatewayId] property.
 */
@DeckExperimental
suspend fun GatewayOrchestrator.dispatchEvent(
    event: GatewayEvent
) = _globalEventsFlow.emit(event)

@DeckDSL
inline fun <reified T : GatewayEvent> GatewayOrchestrator.on(
    scope: CoroutineScope = this,
    noinline callback: suspend T.() -> Unit
): Job = on(null, scope, globalEventsFlow, callback)

@DeckDSL
suspend inline fun <reified T : GatewayEvent> GatewayOrchestrator.await(
    timeout: Long = 4000,
    scope: CoroutineScope = this,
): T? = await(null, scope, globalEventsFlow, timeout)

@DeckDSL
inline fun <reified T : GatewayEvent> Gateway.on(
    scope: CoroutineScope = this.scope,
    noinline callback: suspend T.() -> Unit
): Job = on(gatewayId, scope, eventSharedFlow, callback)

@DeckDSL
suspend inline fun <reified T : GatewayEvent> Gateway.await(
    timeout: Long = 4000,
    scope: CoroutineScope = this.scope,
): T? = await(gatewayId, scope, eventSharedFlow, timeout)

inline fun <reified T : GatewayEvent> on(
    gatewayId: Int?,
    scope: CoroutineScope,
    eventsFlow: SharedFlow<GatewayEvent>,
    noinline callback: suspend T.() -> Unit
): Job = eventsFlow.buffer(Channel.UNLIMITED).filterIsInstance<T>().filter { it.gatewayId == (gatewayId ?: return@filter true) }.onEach(callback).launchIn(scope)

@OptIn(ExperimentalCoroutinesApi::class)
suspend inline fun <reified T : GatewayEvent> await(
    gatewayId: Int?,
    scope: CoroutineScope,
    eventsFlow: SharedFlow<GatewayEvent>,
    timeout: Long = 4000
): T? = withTimeoutOrNull(timeout) {
    suspendCancellableCoroutine<T> { continuation ->
        scope.launch {
            val event = eventsFlow.buffer(Channel.UNLIMITED).filterIsInstance<T>().filter { it.gatewayId == (gatewayId ?: return@filter true) }.first()
            continuation.resume(event) {}
        }
    }
}