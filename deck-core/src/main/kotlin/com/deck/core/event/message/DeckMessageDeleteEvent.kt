package com.deck.core.event.message

import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.core.stateless.StatelessMessage
import com.deck.core.stateless.StatelessServer
import com.deck.core.stateless.channel.StatelessMessageChannel
import com.deck.core.util.BlankStatelessMessage
import com.deck.gateway.event.type.GatewayChatMessageDeletedEvent
import kotlinx.datetime.Instant

public data class DeckMessageDeleteEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    public val message: StatelessMessage,
    public val server: StatelessServer?,
    public val channel: StatelessMessageChannel,
    public val deletedAt: Instant
): DeckEvent {
    public companion object: EventMapper<GatewayChatMessageDeletedEvent, DeckMessageDeleteEvent> {
        override suspend fun map(client: DeckClient, event: GatewayChatMessageDeletedEvent): DeckMessageDeleteEvent {
            val message = BlankStatelessMessage(
                client = client,
                id = event.message.id.mapToBuiltin(),
                channelId = event.message.channelId.mapToBuiltin(),
                serverId = event.serverId
            )
            return DeckMessageDeleteEvent(
                client = client,
                gatewayId = event.gatewayId,
                message = message,
                server = message.channel.server,
                channel = message.channel,
                deletedAt = event.message.deletedAt
            )
        }
    }
}