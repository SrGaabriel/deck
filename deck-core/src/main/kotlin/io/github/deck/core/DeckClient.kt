package io.github.deck.core

import io.github.deck.common.util.GenericId
import io.github.deck.core.event.DefaultEventService
import io.github.deck.core.util.ClientBuilder
import io.github.deck.core.util.WrappedEventSupplier
import io.github.deck.core.util.WrappedEventSupplierData
import io.github.deck.gateway.GatewayOrchestrator
import io.github.deck.gateway.start
import io.github.deck.gateway.util.EventSupplier
import io.github.deck.gateway.util.EventSupplierData
import io.github.deck.rest.RestClient

public class DeckClient internal constructor(
    public val rest: RestClient,
    public val gateway: GatewayOrchestrator,
) : EventSupplier, WrappedEventSupplier {
    public var eventService: DefaultEventService = DefaultEventService(this)

    override val eventSupplierData: EventSupplierData by gateway::eventSupplierData
    override val wrappedEventSupplierData: WrappedEventSupplierData by eventService::wrappedEventSupplierData

    // public val entityDelegator: EntityDelegator = DeckEntityDelegator()

    // workaround
    private val masterGateway = gateway.openGateway()
    public val selfId: GenericId get() = masterGateway.hello.self.id

    public suspend fun login() {
        masterGateway.start()
        eventService.let {
            it.ready()
            it.listen()
        }
    }

    public companion object {
        public operator fun invoke(token: String, builder: ClientBuilder.() -> Unit = {}): DeckClient =
            ClientBuilder(token).apply(builder).build()
    }
}