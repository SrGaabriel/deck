package com.deck.gateway

import com.deck.common.util.AuthenticationResult
import com.deck.common.util.GenericId
import com.deck.gateway.util.Event
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.coroutines.CoroutineContext

class GatewayOrchestrator(val authentication: AuthenticationResult): CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Default
    private val httpClient = HttpClient(CIO.create()) {
        install(WebSockets)
    }
    val gateways = mutableListOf<Gateway>()
    private val _globalEventsFlow = MutableSharedFlow<Event>()
    val globalEventsFlow = _globalEventsFlow.asSharedFlow()

    fun openGateway(parameters: GatewayParameters = GatewayParameters(guildedClientId = authentication.midSession)): Gateway =
        DefaultGateway(this, parameters, client = httpClient, eventSharedFlow = _globalEventsFlow).also { gateways.add(it) }

    fun openTeamGateway(teamId: GenericId) =
        openGateway(GatewayParameters(teamId = teamId, guildedClientId = authentication.midSession))

    fun getOrCreateGatewayForTeam(teamId: GenericId) =
        gateways.firstOrNull { it.parameters.teamId == teamId }
}