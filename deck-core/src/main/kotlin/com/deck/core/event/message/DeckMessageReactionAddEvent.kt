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

public data class DeckMessageReactionAddEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    override val user: StatelessUser,
    override val message: StatelessMessage,
    val team: StatelessTeam?,
    val channel: StatelessMessageChannel,
    val reaction: CustomReaction,
): DeckEvent, UserEvent, MessageEvent {
    public companion object: EventMapper<GatewayChatMessageReactionAddedEvent, DeckMessageReactionAddEvent> {
        override suspend fun map(
            client: DeckClient,
            event: GatewayChatMessageReactionAddedEvent,
        ): DeckMessageReactionAddEvent {
            val team: StatelessTeam? = event.teamId.asNullable()?.let { teamId -> BlankStatelessTeam(client, teamId) }
            val channel = BlankStatelessMessageChannel(client, event.channelId.mapToBuiltin(), team)
            return DeckMessageReactionAddEvent(
                client = client,
                gatewayId = event.gatewayId,
                user = BlankStatelessUser(client, event.reaction.createdBy.getValue()),
                team = team,
                channel = channel,
                reaction = event.reaction.customReaction.map { reaction ->
                    CustomReaction(
                        id = reaction.id,
                        name = reaction.name,
                        png = reaction.png,
                        webp = reaction.webp,
                        apng = reaction.apgn.asNullable()
                    )
                }.getValue(),
                message = BlankStatelessMessage(client, event.message.id.mapToBuiltin(), channel)
            )
        }
    }
}