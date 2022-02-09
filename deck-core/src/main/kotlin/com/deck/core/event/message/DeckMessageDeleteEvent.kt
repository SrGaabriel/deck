package com.deck.core.event.message

import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.Message
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.channel.StatelessMessageChannel
import com.deck.core.util.BlankStatelessMessageChannel
import com.deck.core.util.BlankStatelessTeam
import com.deck.gateway.event.type.GatewayChatMessageDeletedEvent
import kotlinx.datetime.Instant
import java.util.*

public data class DeckMessageDeleteEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    val messageId: UUID,
    val messageCreatedAt: Instant,
    val messageDeletedAt: Instant,
    val channel: StatelessMessageChannel,
    val team: StatelessTeam?,
    val oldMessage: Message?
): DeckEvent {
    public companion object: EventMapper<GatewayChatMessageDeletedEvent, DeckMessageDeleteEvent> {
        override suspend fun map(client: DeckClient, event: GatewayChatMessageDeletedEvent): DeckMessageDeleteEvent {
            val team: StatelessTeam? = event.teamId?.let { teamId -> BlankStatelessTeam(client, teamId) }
            val messageId: UUID = event.message.id.mapToBuiltin()
            return DeckMessageDeleteEvent(
                client = client,
                gatewayId = event.gatewayId,
                messageId = messageId,
                messageCreatedAt = event.message.createdAt,
                messageDeletedAt = event.message.deletedAt,
                channel = BlankStatelessMessageChannel(client, event.channelId.mapToBuiltin(), team),
                team = team,
                oldMessage = client.entityCacheManager.retrieveMessage(messageId)
            )
        }
    }
}