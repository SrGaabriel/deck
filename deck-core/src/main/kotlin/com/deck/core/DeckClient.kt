package com.deck.core

import com.deck.common.util.GenericId
import com.deck.core.event.DefaultEventService
import com.deck.core.event.EventService
import com.deck.core.util.ClientBuilder
import com.deck.core.util.WrappedEventSupplier
import com.deck.core.util.WrappedEventSupplierData
import com.deck.gateway.GatewayOrchestrator
import com.deck.gateway.start
import com.deck.gateway.util.EventSupplier
import com.deck.gateway.util.EventSupplierData
import com.deck.rest.RestClient

public class DeckClient internal constructor(
    public val rest: RestClient,
    public val gateway: GatewayOrchestrator
) : EventSupplier, WrappedEventSupplier {
    public var eventService: EventService = DefaultEventService(this)

    override val eventSupplierData: EventSupplierData by gateway::eventSupplierData
    override val wrappedEventSupplierData: WrappedEventSupplierData by eventService::wrappedEventSupplierData

    // public val entityDelegator: EntityDelegator = DeckEntityDelegator()

    // workaround
    private val masterGateway = gateway.openGateway()
    public val selfId: GenericId get() = masterGateway.hello.self.id

    public suspend fun login() {
        masterGateway.start()
        eventService.startListening()
    }

    public companion object {
        public operator fun invoke(token: String, builder: ClientBuilder.() -> Unit = {}): DeckClient =
            ClientBuilder(token).apply(builder).build()
    }
}