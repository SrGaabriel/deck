package com.deck.gateway

import com.deck.common.util.Constants
import com.deck.common.util.DeckUnknown
import com.deck.common.util.GenericId
import com.deck.gateway.event.DefaultEventDecoder
import com.deck.gateway.event.EventDecoder
import com.deck.gateway.util.Event
import com.deck.gateway.util.GatewayHelloPayload
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Base gateway interface
 */
interface Gateway {
    val scope: CoroutineScope
    val parameters: GatewayParameters
    val eventSharedFlow: SharedFlow<Event>
    val webSocketSession: DefaultWebSocketSession

    suspend fun connect()

    suspend fun startHeartbeat(): Job

    suspend fun startListening(): Job

    suspend fun disconnect(expectingReconnect: Boolean = false)
}

/**
 *
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
    override val scope: CoroutineScope,
    override val parameters: GatewayParameters,
    override val eventSharedFlow: MutableSharedFlow<Event>,
    private val client: HttpClient
): Gateway {
    override lateinit var webSocketSession: DefaultWebSocketSession

    private var eventDecoder: EventDecoder = DefaultEventDecoder()

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
     * Uses a ticker channel to schedule heartbeats/pings
     */
    @OptIn(ObsoleteCoroutinesApi::class)
    override suspend fun startHeartbeat(): Job = scope.launch {
        val helloPayload = receiveHelloPayload()
        val tickerChannel = ticker(helloPayload.pingInterval, helloPayload.pingTimeout)
        tickerChannel.consumeAsFlow().collect {
            webSocketSession.send(Constants.GatewayPingContent)
        }
    }.also { heartbeatJob = it }

    /**
     * Starts collecting payloads from gateway, and ignores them
     * if they're Pong (response to heartbeats) payloads.
     */
    override suspend fun startListening(): Job = scope.launch {
        webSocketSession.incoming.receiveAsFlow().filterIsInstance<Frame.Text>().collect {
            if (it.data.contentEquals(Constants.GatewayPongContent.toByteArray())) return@collect
            val json = eventDecoder.decodeJsonFromPayload(it.data.decodeToString()) ?: return@collect
            val event = eventDecoder.decodeEventFromJson(json) ?: return@collect
            eventSharedFlow.emit(event)
        }
    }.also { listeningJob = it }

    override suspend fun disconnect(expectingReconnect: Boolean) {
        val code = if (expectingReconnect) CloseReason.Codes.SERVICE_RESTART else CloseReason.Codes.GOING_AWAY
        heartbeatJob.cancel("Shutdown")
        listeningJob.cancel("Shutdown")
        webSocketSession.close(reason = CloseReason(code, "Shutdown"))
    }

    @Deprecated("It's only decoding the first request", replaceWith = ReplaceWith("No solution yet"))
    private suspend fun receiveHelloPayload() =
        Json.decodeFromString<GatewayHelloPayload>(webSocketSession.incoming.receive().data.decodeToString().substring(1))
}

inline fun <reified T : Event> Gateway.on(noinline callback: suspend T.() -> Unit): Job =
    eventSharedFlow.buffer(Channel.UNLIMITED).filterIsInstance<T>().onEach(callback).launchIn(scope)

suspend inline fun <reified T : Event> Gateway.await(timeout: Long = 1000): T? = withTimeoutOrNull(timeout) {
    suspendCancellableCoroutine<T> { continuation ->
        scope.launch {
            val event = eventSharedFlow.buffer(Channel.UNLIMITED).filterIsInstance<T>().first()
            continuation.resume(event) {}
        }
    }
}