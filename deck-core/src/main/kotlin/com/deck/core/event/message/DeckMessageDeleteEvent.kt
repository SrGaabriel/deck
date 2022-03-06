package com.deck.core.event.message

import com.deck.common.util.GenericId
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.Message
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.core.event.MessageEvent
import com.deck.core.stateless.StatelessMessage
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.channel.StatelessMessageChannel
import com.deck.core.util.BlankStatelessMessage
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
    val channelId: UUID,
    val teamId: GenericId?,
    val oldMessage: Message?
): DeckEvent, MessageEvent {
    @Deprecated("Since message has been deleted, any action involving it will be answered by an 505 on guilded's end.", replaceWith = ReplaceWith("messageId"))
    override val message: StatelessMessage get() = BlankStatelessMessage(client, messageId, channelId, teamId)
    val channel: StatelessMessageChannel get() = BlankStatelessMessageChannel(client, channelId, teamId)
    val team: StatelessTeam? get() = teamId?.let { BlankStatelessTeam(client, it) }

    public companion object: EventMapper<GatewayChatMessageDeletedEvent, DeckMessageDeleteEvent> {
        override suspend fun map(client: DeckClient, event: GatewayChatMessageDeletedEvent): DeckMessageDeleteEvent {
            val messageId: UUID = event.message.id.mapToBuiltin()
            return DeckMessageDeleteEvent(
                client = client,
                gatewayId = event.gatewayId,
                messageId = messageId,
                messageCreatedAt = event.message.createdAt,
                messageDeletedAt = event.message.deletedAt,
                channelId = event.channelId.mapToBuiltin(),
                teamId = event.teamId,
                oldMessage = client.cache.retrieveMessage(messageId)
            )
        }
    }
}