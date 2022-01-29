package com.deck.core.module

import com.deck.gateway.Gateway
import com.deck.gateway.GatewayOrchestrator
import com.deck.gateway.start
import com.deck.gateway.util.EventSupplier
import com.deck.gateway.util.EventSupplierData

public interface GatewayModule : EventSupplier {
    public val orchestrator: GatewayOrchestrator

    public val gateways: Map<Int, Gateway>
    public val globalGateway: Gateway

    public var logPayloadsJson: Boolean

    public suspend fun start()
}

public class DefaultGatewayModule(token: String) : GatewayModule {
    override val orchestrator: GatewayOrchestrator = GatewayOrchestrator(token)
    override lateinit var globalGateway: Gateway

    override var logPayloadsJson: Boolean by orchestrator::debugPayloads

    override val gateways: Map<Int, Gateway> get() = orchestrator.gateways.associateBy { it.gatewayId }

    override val eventSupplierData: EventSupplierData by orchestrator::eventSupplierData

    override suspend fun start() {
        globalGateway = orchestrator.openGateway()
        orchestrator.gateways.forEach { gateway ->
            gateway.start()
        }
    }
}