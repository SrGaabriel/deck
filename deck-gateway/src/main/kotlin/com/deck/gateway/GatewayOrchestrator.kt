package com.deck.gateway

import com.deck.common.util.AuthenticationResult
import com.deck.common.util.GenericId
import com.deck.gateway.event.GatewayEvent
import com.deck.gateway.util.EventSupplier
import com.deck.gateway.util.EventSupplierData
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

public class GatewayOrchestrator: EventSupplier, CoroutineScope {
    override val coroutineContext: CoroutineContext by lazy {
        Executors
            .newFixedThreadPool(4)
            .asCoroutineDispatcher()
    }
    private val httpClient = HttpClient(CIO.create()) {
        install(WebSockets)
    }

    public lateinit var authentication: AuthenticationResult
    override val eventSupplierData: EventSupplierData by lazy {
        EventSupplierData(
            scope = this,
            sharedFlow = globalEventsFlow
        )
    }

    // When enabled prints payloads json
    public var debugPayloads: Boolean = false
    public val gateways: MutableList<Gateway> = mutableListOf()
    public val globalEventsFlow: MutableSharedFlow<GatewayEvent> = MutableSharedFlow()

    // We'll use a different counter since we don't want a previously closed gateway and a new one having same IDs
    private var gatewayCurrentId = 0
    public fun openGateway(parameters: GatewayParameters = GatewayParameters(guildedClientId = authentication.midSession)): Gateway =
        DefaultGateway(
            authentication.token,
            debugPayloads,
            gatewayCurrentId.also { gatewayCurrentId++ },
            this,
            parameters,
            client = httpClient,
            eventSharedFlow = globalEventsFlow
        ).also { gateways.add(it) }

    public fun openTeamGateway(teamId: GenericId): Gateway =
        openGateway(GatewayParameters(teamId = teamId, guildedClientId = authentication.midSession))

    public suspend fun closeGateway(gatewayId: Int): Unit =
        closeGateway(gateways.first { it.gatewayId == gatewayId })

    public suspend fun closeGateway(teamId: GenericId): Unit =
        closeGateway(gateways.first { it.parameters.teamId == teamId })

    public suspend fun closeGateway(gateway: Gateway): Unit =
        gateways.remove(gateway.also { gateway.disconnect(false) }).let {}
}