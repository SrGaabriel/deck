package com.deck.core.event.message

import com.deck.core.DeckClient
import com.deck.core.entity.Message
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.core.stateless.StatelessServer
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessMessageChannel
import com.deck.gateway.event.type.GatewayChatMessageUpdatedEvent

public data class DeckMessageUpdateEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    public val message: Message,
    val channel: StatelessMessageChannel,
    val author: StatelessUser,
    val server: StatelessServer?,
) : DeckEvent {
    public companion object: EventMapper<GatewayChatMessageUpdatedEvent, DeckMessageUpdateEvent> {
        override suspend fun map(client: DeckClient, event: GatewayChatMessageUpdatedEvent): DeckMessageUpdateEvent {
            val message = client.entityDecoder.decodeMessage(event.message)
            return DeckMessageUpdateEvent(
                client = client,
                gatewayId = event.gatewayId,
                message = message,
                channel = message.channel,
                server = message.server,
                author = message.author
            )
        }
    }
}