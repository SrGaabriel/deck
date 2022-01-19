package com.deck.core.module

import com.deck.common.util.AuthenticationResult
import com.deck.common.util.GenericId
import com.deck.gateway.Gateway
import com.deck.gateway.GatewayOrchestrator
import com.deck.gateway.start
import com.deck.gateway.util.EventSupplier
import com.deck.gateway.util.EventSupplierData

public interface GatewayModule : EventSupplier {
    public val orchestrator: GatewayOrchestrator
    public val globalGateway: Gateway

    public val gateways: Map<Int, Gateway>

    public var auth: AuthenticationResult
    public var logPayloadsJson: Boolean

    public suspend fun start()

    public suspend fun openGateway(teamId: GenericId? = null): Gateway

    public suspend fun closeGateway(teamId: GenericId? = null)
}

public class DefaultGatewayModule : GatewayModule {
    override val orchestrator: GatewayOrchestrator = GatewayOrchestrator()
    override lateinit var globalGateway: Gateway

    override var auth: AuthenticationResult by orchestrator::authentication
    override var logPayloadsJson: Boolean by orchestrator::debugPayloads

    override val gateways: Map<Int, Gateway> get() = orchestrator.gateways.associateBy { it.gatewayId }

    override val eventSupplierData: EventSupplierData by orchestrator::eventSupplierData

    override suspend fun start() {
        globalGateway = orchestrator.openGateway()
        orchestrator.gateways.forEach { gateway ->
            gateway.start()
        }
    }

    override suspend fun openGateway(teamId: GenericId?): Gateway =
        if (teamId == null) orchestrator.openGateway() else orchestrator.openTeamGateway(teamId)

    override suspend fun closeGateway(teamId: GenericId?) {
        if (teamId == null) orchestrator.closeGateway(globalGateway) else orchestrator.closeGateway(teamId)
    }
}
