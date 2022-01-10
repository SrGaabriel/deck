package com.deck.gateway.util

import com.deck.common.util.Constants
import com.deck.common.util.DeckUnknown
import com.deck.common.util.GenericId
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

/**
 * Base gateway interface
 */
interface Gateway {
    val gatewayId: Int
    val scope: CoroutineScope
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
    private val client: HttpClient
): Gateway {
    override lateinit var webSocketSession: DefaultWebSocketSession

    private var eventDecoder: EventDecoder = DefaultEventDecoder(gatewayId)

    private lateinit var listeningJob: Job
    private lateinit var heartbeatJob: Job

    /**
     * Creates the websocket session and invokes [startHeartbeat] and [startListening],
     * which also prevents the application from stopping but doesn't block the main thread.
     *
     * @see startHeartbeat
     * @see startListening
     */
    override suspend fun connect() {
        webSocketSession = client.webSocketSession(host = Constants.GuildedGateway, path = parameters.buildPath())
        startListening()
        startHeartbeat()
    }

    /**
     * Starts collecting payloads from gateway, and ignores them
     * if they're Pong (response to heartbeats) payloads.
     */
    override suspend fun startListening(): Job = scope.launch {
        webSocketSession.incoming.receiveAsFlow().filterIsInstance<Frame.Text>().collect {
            if (it.data.contentEquals(Constants.GatewayPongContent.toByteArray())) return@collect
            val json = eventDecoder.decodePayloadFromString(it.readText()) ?: return@collect
            val event = eventDecoder.decodeEventFromPayload(json) ?: return@collect
            eventSharedFlow.emit(event)
        }
    }.also { listeningJob = it }

    /**
     * Uses a ticker channel to schedule heartbeats/pings. Needs to be called
     * after [startListening] so it can handle the [GatewayHelloEvent] event.
     */
    @OptIn(ObsoleteCoroutinesApi::class)
    override suspend fun startHeartbeat(): Job = scope.launch {
        val helloPayload = await<GatewayHelloEvent>() ?: error("Gateway hello payload wasn't sent in time in gateway $gatewayId.")
        val tickerChannel = ticker(helloPayload.pingInterval, helloPayload.pingTimeout)
        tickerChannel.consumeAsFlow().collect {
            webSocketSession.send(Constants.GatewayPingContent)
        }
    }.also { heartbeatJob = it }

    override suspend fun disconnect(expectingReconnect: Boolean) {
        val code = if (expectingReconnect) CloseReason.Codes.SERVICE_RESTART else CloseReason.Codes.GOING_AWAY
        heartbeatJob.cancel("Shutdown")
        listeningJob.cancel("Shutdown")
        webSocketSession.close(reason = CloseReason(code, "Shutdown"))
    }
}

inline fun <reified T : GatewayEvent> GatewayOrchestrator.on(
    scope: CoroutineScope = this,
    noinline callback: suspend T.() -> Unit
): Job = on(null, scope, globalEventsFlow, callback)

suspend inline fun <reified T : GatewayEvent> GatewayOrchestrator.await(
    timeout: Long = 4000,
    scope: CoroutineScope = this,
): T? = await(null, scope, globalEventsFlow, timeout)

inline fun <reified T : GatewayEvent> Gateway.on(
    scope: CoroutineScope = this.scope,
    noinline callback: suspend T.() -> Unit
): Job = on(gatewayId, scope, eventSharedFlow, callback)

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