package com.deck.core.event.message

import com.deck.common.content.node.decode
import com.deck.common.util.asNullable
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.Message
import com.deck.core.entity.Team
import com.deck.core.entity.channel.Channel
import com.deck.core.entity.impl.DeckMessage
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
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

public data class DeckMessageCreateEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    val message: Message,
    val channel: StatelessMessageChannel,
    val team: StatelessTeam?,
    val member: StatelessMember?,
    override val user: StatelessUser,
) : DeckEvent, UserEvent {
    public suspend fun getTeam(): Team? = team?.getState()

    public suspend fun getChannel(): Channel = channel.getState()

    public companion object : EventMapper<GatewayChatMessageCreatedEvent, DeckMessageCreateEvent> {
        override suspend fun map(client: DeckClient, event: GatewayChatMessageCreatedEvent): DeckMessageCreateEvent {
            val team = event.teamId.asNullable()?.let { BlankStatelessTeam(client, it) }
            val channel = BlankStatelessMessageChannel(
                client = client,
                id = event.channelId.mapToBuiltin(),
                team = team
            )
            val message = DeckMessage(
                client = client,
                id = event.message.id.mapToBuiltin(),
                content = event.message.content.decode(),
                team = team,
                repliesToId = event.message.repliesTo?.mapToBuiltin(),
                createdAt = event.createdAt,
                author = BlankStatelessUser(client, event.createdBy),
                updatedAt = null,
                editor = null,
                channel = channel,
                isPrivate = event.message.isPrivate.asNullable() == true
            )
            val member = team?.let { memberTeam -> BlankStatelessMember(client, event.createdBy, memberTeam) }
            return DeckMessageCreateEvent(
                client = client,
                gatewayId = event.gatewayId,
                message = message,
                user = BlankStatelessUser(client, event.createdBy),
                team = team,
                channel = channel,
                member = member
            )
        }
    }
}