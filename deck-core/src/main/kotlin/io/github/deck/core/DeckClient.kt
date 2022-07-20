package io.github.deck.core

import io.github.deck.common.util.GenericId
import io.github.deck.core.event.DefaultEventService
import io.github.deck.core.util.ClientBuilder
import io.github.deck.gateway.GatewayOrchestrator
import io.github.deck.gateway.event.type.GatewayHelloEvent
import io.github.deck.gateway.start
import io.github.deck.gateway.util.on
import io.github.deck.rest.RestClient

public class DeckClient internal constructor(
    public val rest: RestClient,
    public val gateway: GatewayOrchestrator,
) {
    public var eventService: DefaultEventService = DefaultEventService(this)

    // public val entityDelegator: EntityDelegator = DeckEntityDelegator()

    // workaround
    public lateinit var selfId: GenericId

    public suspend fun login() {
        val masterGateway = gateway.createGateway()
        eventService.let {
            it.ready()
            it.listen()
        }
        masterGateway.on<GatewayHelloEvent> {
            selfId = self.id
        }
        masterGateway.start()
    }

    public companion object {
        public operator fun invoke(token: String, builder: ClientBuilder.() -> Unit = {}): DeckClient =
            ClientBuilder(token).apply(builder).build()
    }
}