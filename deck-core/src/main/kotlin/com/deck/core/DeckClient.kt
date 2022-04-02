package com.deck.core

import com.deck.common.util.DeckExperimental
import com.deck.core.event.DefaultEventService
import com.deck.core.event.EventService
import com.deck.core.proxy.DeckEntityDecoder
import com.deck.core.proxy.EntityDecoder
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
    @DeckExperimental
    public var eventService: EventService = DefaultEventService(this)

    override val eventSupplierData: EventSupplierData by gateway::eventSupplierData
    override val wrappedEventSupplierData: WrappedEventSupplierData by eventService::wrappedEventSupplierData

    public val entityDecoder: EntityDecoder = DeckEntityDecoder(this)
    // public val entityDelegator: EntityDelegator = DeckEntityDelegator()

    public suspend fun login() {
        gateway
            .openGateway()
            .start()
        eventService.startListening()
    }

    public companion object {
        public operator fun invoke(token: String, builder: ClientBuilder.() -> Unit = {}): DeckClient =
            ClientBuilder(token).apply(builder).build()
    }
}