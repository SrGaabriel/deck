package com.deck.gateway

import com.deck.common.util.AuthenticationResult
import com.deck.common.util.GenericId
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class GatewayOrchestrator(val authentication: AuthenticationResult): CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Default
    private val httpClient = HttpClient(CIO.create()) {
        install(WebSockets)
    }

    fun openGateway(parameters: GatewayParameters = GatewayParameters(guildedClientId = authentication.midSession)): Gateway =
        StandardGateway(this, parameters, httpClient)

    fun openTeamGateway(teamId: GenericId) = openGateway(GatewayParameters(teamId = teamId, guildedClientId = authentication.midSession))
}