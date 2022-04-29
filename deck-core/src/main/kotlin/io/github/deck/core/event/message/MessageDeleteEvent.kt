package io.github.deck.core.event.message

import io.github.deck.common.util.GenericId
import io.github.deck.common.util.mapToBuiltin
import io.github.deck.core.DeckClient
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.util.BlankStatelessMessage
import io.github.deck.gateway.event.type.GatewayChatMessageDeletedEvent
import kotlinx.datetime.Instant
import java.util.*

public data class MessageDeleteEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    public val messageId: UUID,
    public val channelId: UUID,
    public val serverId: GenericId?,
    public val deletedAt: Instant
): DeckEvent {
    public companion object: EventMapper<GatewayChatMessageDeletedEvent, MessageDeleteEvent> {
        override suspend fun map(client: DeckClient, event: GatewayChatMessageDeletedEvent): MessageDeleteEvent {
            val message = BlankStatelessMessage(
                client = client,
                id = event.message.id.mapToBuiltin(),
                channelId = event.message.channelId.mapToBuiltin(),
                serverId = event.serverId
            )
            return MessageDeleteEvent(
                client = client,
                gatewayId = event.gatewayId,
                messageId = message.id,
                serverId = message.serverId,
                channelId = message.channelId,
                deletedAt = event.message.deletedAt
            )
        }
    }
}