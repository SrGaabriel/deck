package com.deck.core.event.message

import com.deck.common.content.node.decode
import com.deck.common.util.GenericId
import com.deck.common.util.asNullable
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.Message
import com.deck.core.entity.Team
import com.deck.core.entity.channel.Channel
import com.deck.core.entity.impl.DeckMessage
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.core.event.MessageEvent
import com.deck.core.event.UserEvent
import com.deck.core.stateless.StatelessMember
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessMessageChannel
import com.deck.core.util.BlankStatelessMember
import com.deck.core.util.BlankStatelessMessageChannel
import com.deck.core.util.BlankStatelessTeam
import com.deck.core.util.BlankStatelessUser
import com.deck.gateway.event.type.GatewayChatMessageCreatedEvent
import java.util.*

public data class DeckMessageCreateEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    override val message: Message,
    val channelId: UUID,
    val teamId: GenericId?,
    val userId: GenericId,
) : DeckEvent, UserEvent, MessageEvent {
    val channel: StatelessMessageChannel get() = BlankStatelessMessageChannel(client, channelId, teamId)
    val team: StatelessTeam? get() = teamId?.let { BlankStatelessTeam(client, it) }
    val member: StatelessMember? get() = teamId?.let { BlankStatelessMember(client, userId, it) }
    override val user: StatelessUser get() = BlankStatelessUser(client, userId)

    public suspend fun getTeam(): Team? = team?.getState()

    public suspend fun getChannel(): Channel = channel.getState()

    public companion object : EventMapper<GatewayChatMessageCreatedEvent, DeckMessageCreateEvent> {
        override suspend fun map(client: DeckClient, event: GatewayChatMessageCreatedEvent): DeckMessageCreateEvent {
            val channelId = event.channelId.mapToBuiltin()
            val message = DeckMessage(
                client = client,
                id = event.message.id.mapToBuiltin(),
                content = event.message.content.decode(),
                teamId = event.teamId.asNullable(),
                repliesToId = event.message.repliesTo?.mapToBuiltin(),
                createdAt = event.createdAt,
                authorId = event.createdBy,
                updatedAt = null,
                editorId = null,
                channelId = channelId,
                isPrivate = event.message.isPrivate.asNullable() == true
            )
            return DeckMessageCreateEvent(
                client = client,
                gatewayId = event.gatewayId,
                message = message,
                userId = event.createdBy,
                teamId = event.teamId.asNullable(),
                channelId = channelId
            )
        }
    }
}