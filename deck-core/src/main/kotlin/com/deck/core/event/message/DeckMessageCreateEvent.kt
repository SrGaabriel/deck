package com.deck.core.event.message

import com.deck.core.DeckClient
import com.deck.core.entity.Message
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.gateway.event.type.GatewayChatMessageCreatedEvent

public data class DeckMessageCreateEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    public val message: Message
) : DeckEvent {
    public companion object: EventMapper<GatewayChatMessageCreatedEvent, DeckMessageCreateEvent> {
        override suspend fun map(client: DeckClient, event: GatewayChatMessageCreatedEvent): DeckMessageCreateEvent = DeckMessageCreateEvent(
            client = client,
            gatewayId = event.gatewayId,
            message = client.entityDecoder.decodeMessage(event.message)
        )
    }
}