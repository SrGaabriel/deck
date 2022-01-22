package com.deck.core.event.message

import com.deck.common.content.node.NodeGlobalStrategy
import com.deck.common.util.asNullable
import com.deck.common.util.map
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.Channel
import com.deck.core.entity.Message
import com.deck.core.entity.Team
import com.deck.core.entity.User
import com.deck.core.entity.impl.DeckMessage
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.core.stateless.StatelessMessageChannel
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.StatelessUser
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
    val sender: StatelessUser
) : DeckEvent {
    public suspend fun getTeam(): Team? = team?.getState()

    public suspend fun getSender(): User = sender.getState()

    public suspend fun getChannel(): Channel = channel.getState()

    public companion object : EventMapper<GatewayChatMessageCreatedEvent, DeckMessageCreateEvent> {
        override suspend fun map(client: DeckClient, event: GatewayChatMessageCreatedEvent): DeckMessageCreateEvent {
            val channel = BlankStatelessMessageChannel(
                client = client,
                id = event.channelId.mapToBuiltin(),
                teamId = event.teamId.asNullable()
            )
            val message = DeckMessage(
                client = client,
                id = event.message.id.mapToBuiltin(),
                content = NodeGlobalStrategy.decodeContent(event.message.content),
                teamId = event.teamId.asNullable(),
                repliesToId = event.message.repliesTo?.mapToBuiltin(),
                createdAt = event.createdAt,
                createdBy = event.createdBy,
                updatedAt = null,
                updatedBy = null,
                channel = channel,
                isSilent = event.message.isSilent.asNullable() == true,
                isPrivate = event.message.isPrivate.asNullable() == true
            )
            return DeckMessageCreateEvent(
                client = client,
                gatewayId = event.gatewayId,
                message = message,
                sender = BlankStatelessUser(client, event.createdBy),
                team = event.teamId.map { BlankStatelessTeam(client, it) }.asNullable(),
                channel = channel
            )
        }
    }
}
