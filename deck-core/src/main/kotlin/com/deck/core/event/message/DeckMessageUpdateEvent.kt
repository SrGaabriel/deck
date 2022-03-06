package com.deck.core.event.message

import com.deck.common.content.node.decode
import com.deck.common.util.GenericId
import com.deck.common.util.asNullable
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.Message
import com.deck.core.entity.impl.DeckMessage
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.core.event.MessageEvent
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessMessageChannel
import com.deck.core.util.BlankStatelessMessageChannel
import com.deck.core.util.BlankStatelessTeam
import com.deck.core.util.BlankStatelessUser
import com.deck.gateway.event.type.GatewayChatMessageUpdatedEvent
import java.util.*

public data class DeckMessageUpdateEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    override val message: Message,
    val channelId: UUID,
    val teamId: GenericId?,
    val senderId: GenericId,
    val editorId: GenericId,
    val oldMessage: Message?
): DeckEvent, MessageEvent {
    val channel: StatelessMessageChannel get() = BlankStatelessMessageChannel(client, channelId, teamId)
    val team: StatelessTeam? get() = teamId?.let { BlankStatelessTeam(client, it) }
    val sender: StatelessUser get() = BlankStatelessUser(client, senderId)
    val editor: StatelessUser get() = BlankStatelessUser(client, editorId)

    public companion object: EventMapper<GatewayChatMessageUpdatedEvent, DeckMessageUpdateEvent> {
        override suspend fun map(client: DeckClient, event: GatewayChatMessageUpdatedEvent): DeckMessageUpdateEvent {
            val channelId = event.channelId.mapToBuiltin()
            val message = DeckMessage(
                client = client,
                id = event.message.id.mapToBuiltin(),
                content = event.message.content.decode(),
                teamId = event.teamId.asNullable(),
                repliesToId = event.message.repliesToIds?.firstOrNull()?.mapToBuiltin(),
                createdAt = event.message.createdAt,
                authorId = event.message.createdBy,
                updatedAt = null,
                editorId = event.updatedBy,
                channelId = channelId,
                isPrivate = event.message.isPrivate
            )
            return DeckMessageUpdateEvent(
                client = client,
                gatewayId = event.gatewayId,
                message = message,
                senderId = event.message.createdBy,
                editorId = event.updatedBy,
                teamId = event.teamId.asNullable(),
                channelId = channelId,
                oldMessage = client.cache.retrieveMessage(event.message.id.mapToBuiltin())
            )
        }
    }
}