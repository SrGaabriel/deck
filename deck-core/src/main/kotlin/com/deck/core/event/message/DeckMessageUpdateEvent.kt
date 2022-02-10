package com.deck.core.event.message

import com.deck.common.content.node.decode
import com.deck.common.util.asNullable
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.Message
import com.deck.core.entity.impl.DeckMessage
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.core.stateless.StatelessMember
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessMessageChannel
import com.deck.core.util.BlankStatelessMember
import com.deck.core.util.BlankStatelessMessageChannel
import com.deck.core.util.BlankStatelessTeam
import com.deck.core.util.BlankStatelessUser
import com.deck.gateway.event.type.GatewayChatMessageUpdatedEvent

public data class DeckMessageUpdateEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    val message: Message,
    val channel: StatelessMessageChannel,
    val team: StatelessTeam?,
    val member: StatelessMember?,
    val sender: StatelessUser,
    val editor: StatelessUser,
    val oldMessage: Message?
): DeckEvent {
    public companion object: EventMapper<GatewayChatMessageUpdatedEvent, DeckMessageUpdateEvent> {
        override suspend fun map(client: DeckClient, event: GatewayChatMessageUpdatedEvent): DeckMessageUpdateEvent {
            val team = event.teamId.asNullable()?.let { BlankStatelessTeam(client, it) }
            val channel = BlankStatelessMessageChannel(
                client = client,
                id = event.channelId.mapToBuiltin(),
                team = team
            )
            val sender = BlankStatelessUser(client, event.message.createdBy)
            val editor = BlankStatelessUser(client, event.updatedBy)
            val message = DeckMessage(
                client = client,
                id = event.message.id.mapToBuiltin(),
                content = event.message.content.decode(),
                team = team,
                repliesToId = event.message.repliesToIds?.firstOrNull()?.mapToBuiltin(),
                createdAt = event.message.createdAt,
                author = sender,
                updatedAt = null,
                editor = editor,
                channel = channel,
                isPrivate = event.message.isPrivate
            )
            val member = team?.let { memberTeam -> BlankStatelessMember(client, event.message.createdBy, memberTeam) }
            return DeckMessageUpdateEvent(
                client = client,
                gatewayId = event.gatewayId,
                message = message,
                sender = sender,
                editor = editor,
                team = team,
                channel = channel,
                member = member,
                oldMessage = client.entityCacheManager.retrieveMessage(event.message.id.mapToBuiltin())
            )
        }
    }
}