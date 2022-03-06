package com.deck.core.event.message

import com.deck.common.util.*
import com.deck.core.DeckClient
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.core.event.MessageEvent
import com.deck.core.event.UserEvent
import com.deck.core.stateless.StatelessMessage
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessMessageChannel
import com.deck.core.util.BlankStatelessMessage
import com.deck.core.util.BlankStatelessMessageChannel
import com.deck.core.util.BlankStatelessTeam
import com.deck.core.util.BlankStatelessUser
import com.deck.gateway.event.type.GatewayChatMessageReactionAddedEvent
import java.util.*

public data class DeckMessageReactionAddEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    val userId: GenericId,
    val messageId: UUID,
    val teamId: GenericId?,
    val channelId: UUID,
    val reaction: CustomReaction,
): DeckEvent, UserEvent, MessageEvent {
    val team: StatelessTeam? get() = teamId?.let { BlankStatelessTeam(client, it) }
    val channel: StatelessMessageChannel get() = BlankStatelessMessageChannel(client, channelId, teamId)
    override val user: StatelessUser get() = BlankStatelessUser(client, userId)
    override val message: StatelessMessage get() = BlankStatelessMessage(client, messageId, channelId, teamId)

    public companion object : EventMapper<GatewayChatMessageReactionAddedEvent, DeckMessageReactionAddEvent> {
        override suspend fun map(
            client: DeckClient,
            event: GatewayChatMessageReactionAddedEvent,
        ): DeckMessageReactionAddEvent = DeckMessageReactionAddEvent(
            client = client,
            gatewayId = event.gatewayId,
            userId = event.reaction.createdBy.getValue(),
            teamId = event.teamId.asNullable(),
            channelId = event.channelId.mapToBuiltin(),
            reaction = event.reaction.customReaction.map { reaction ->
                CustomReaction(
                    id = reaction.id,
                    name = reaction.name,
                    png = reaction.png,
                    webp = reaction.webp,
                    apng = reaction.apgn.asNullable()
                )
            }.getValue(),
            messageId = event.message.id.mapToBuiltin()
        )
    }
}