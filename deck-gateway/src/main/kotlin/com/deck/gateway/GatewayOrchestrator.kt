package com.deck.gateway

import com.deck.common.util.AuthenticationResult
import com.deck.common.util.GenericId
import com.deck.gateway.event.GatewayEvent
import com.deck.gateway.util.DefaultGateway
import com.deck.gateway.util.Gateway
import com.deck.gateway.util.GatewayParameters
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

    internal val _globalEventsFlow = MutableSharedFlow<GatewayEvent>()
    val globalEventsFlow = _globalEventsFlow.asSharedFlow()

    // We'll use a different counter since we don't want a previously closed gateway and a new one having same IDs, since this could lead to unexpected errors
    var gatewayCurrentId = 0

    fun openGateway(parameters: GatewayParameters = GatewayParameters(guildedClientId = authentication.midSession)): Gateway =
        DefaultGateway(gatewayCurrentId.also { gatewayCurrentId++ },this, parameters, client = httpClient, eventSharedFlow = _globalEventsFlow).also { gateways.add(it) }

    fun openTeamGateway(teamId: GenericId) =
        openGateway(GatewayParameters(teamId = teamId, guildedClientId = authentication.midSession))

    fun getOrCreateGatewayForTeam(teamId: GenericId): Gateway =
        gateways.firstOrNull { it.parameters.teamId == teamId } ?: openTeamGateway(teamId)

    suspend fun closeGateway(gateway: Gateway) =
        gateways.remove(gateway.also { gateway.disconnect(false) })
}