package com.deck.gateway

import com.deck.common.util.Constants
import com.deck.common.util.GenericId
import com.deck.gateway.payload.GatewayHelloPayload
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

interface Gateway {
    val scope: CoroutineScope
    val webSocketSession: DefaultWebSocketSession

    suspend fun connect()

    suspend fun startHeartbeat(): Job

    suspend fun startListening(): Job

    suspend fun disconnect(expectingReconnect: Boolean = false)
}

data class GatewayParameters(
    val jwt: String = "undefined",
    val eioVersion: Int = 3,
    val transport: String = "websocket",
    val guildedClientId: String,
    val teamId: GenericId? = null
) {
    fun buildPath() = "/?jwt=$jwt&EIO=$eioVersion&transport=$transport&guildedClientId=$guildedClientId".let {
        if (teamId != null) "$it&teamId!=null" else it
    }
}

class StandardGateway(override val scope: CoroutineScope, parameters: GatewayParameters, private val client: HttpClient): Gateway {
    private var path =
        "/socket.io/?jwt=${parameters.jwt}&EIO=${parameters.eioVersion}&transport=${parameters.transport}&guildedClientId=${parameters.guildedClientId}"

    init {
        if (parameters.teamId != null) path += "&teamId=${parameters.teamId}"
    }

    override lateinit var webSocketSession: DefaultWebSocketSession
    private var running: Boolean = true

    override suspend fun connect() {
        webSocketSession = client.webSocketSession(host = Constants.GuildedGateway, path = path)
        running = true
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    override suspend fun startHeartbeat(): Job = scope.launch {
        val tickerChannel = ticker(receiveHelloPayload().pingInterval)
        tickerChannel.consumeAsFlow().collect {
            webSocketSession.send("2")
        }
    }

    override suspend fun startListening(): Job = scope.launch {
        webSocketSession.incoming.receiveAsFlow().filterIsInstance<Frame>().collect {
            if (it.data.contentEquals(Constants.GatewayPongContent.toByteArray())) return@collect
            println(it.data.decodeToString())
        }
    }

    override suspend fun disconnect(expectingReconnect: Boolean) {
        val code = if (expectingReconnect) CloseReason.Codes.SERVICE_RESTART else CloseReason.Codes.GOING_AWAY
        webSocketSession.close(reason = CloseReason(code, "Shutdown"))
    }

    @Deprecated("It's only decoding the first request", replaceWith = ReplaceWith("No solution yet"))
    private suspend fun receiveHelloPayload() =
        Json.decodeFromString<GatewayHelloPayload>(webSocketSession.incoming.receive().data.decodeToString().substring(1))
}