package io.github.deck.core.event.user

import io.github.deck.core.DeckClient
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.gateway.event.type.GatewayHelloEvent

public data class HelloEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    public val heartbeatInterval: Long,
    public val lastMessageId: String
) : DeckEvent {
    public companion object: EventMapper<GatewayHelloEvent, HelloEvent> {
        override suspend fun map(client: DeckClient, event: GatewayHelloEvent): HelloEvent = HelloEvent(
            client = client,
            gatewayId = event.gatewayId,
            heartbeatInterval = event.heartbeatIntervalMs,
            lastMessageId = event.lastMessageId
        )
    }
}