package com.deck.core.event.user

import com.deck.core.DeckClient
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.gateway.event.type.GatewayHelloEvent

public data class DeckHelloEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    public val heartbeatInterval: Long,
    public val lastMessageId: String
) : DeckEvent {
    public companion object: EventMapper<GatewayHelloEvent, DeckHelloEvent> {
        override suspend fun map(client: DeckClient, event: GatewayHelloEvent): DeckHelloEvent = DeckHelloEvent(
            client = client,
            gatewayId = event.gatewayId,
            heartbeatInterval = event.heartbeatIntervalMs,
            lastMessageId = event.lastMessageId
        )
    }
}