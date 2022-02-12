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

    /**
     * Creates a global gateway and starts
     * to listen to all previously created gateways (including the global one)
     */
    public suspend fun start()

    /**
     * Opens a gateway (global if [teamId] is null, or team)
     *
     * @param teamId null if gateway is global
     *
     * @return the created gateway
     */
    public suspend fun openGateway(teamId: GenericId? = null): Gateway

    /**
     * Creates gateways for each [teamIds].
     *
     * @param teamIds all team ids
     */
    public suspend fun openTeamGateways(vararg teamIds: GenericId)

    /**
     * Closes the gateway with the specified [teamId], or the global gateway if not provided.
     *
     * @param teamId team id
     */
    public suspend fun closeGateway(teamId: GenericId? = null)

    /**
     * Closes the gateway with the specified [gatewayId].
     *
     * @param gatewayId gateway id
     */
    public suspend fun closeGateway(gatewayId: Int)
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

    override suspend fun openTeamGateways(vararg teamIds: GenericId) {
        for (team in teamIds) {
            openGateway(team)
        }
    }

    override suspend fun closeGateway(teamId: GenericId?) {
        if (teamId == null) orchestrator.closeGateway(globalGateway) else orchestrator.closeGateway(teamId)
    }

    override suspend fun closeGateway(gatewayId: Int) {
        orchestrator.closeGateway(gatewayId)
    }
}