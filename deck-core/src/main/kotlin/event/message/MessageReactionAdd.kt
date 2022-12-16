package io.github.srgaabriel.deck.core.event.message

import io.github.srgaabriel.deck.common.util.Emote
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.asNullable
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.Message
import io.github.srgaabriel.deck.core.event.DeckEvent
import io.github.srgaabriel.deck.core.event.EventMapper
import io.github.srgaabriel.deck.core.event.EventService
import io.github.srgaabriel.deck.core.event.mapper
import io.github.srgaabriel.deck.core.stateless.StatelessMessage
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.StatelessUser
import io.github.srgaabriel.deck.core.stateless.channel.StatelessMessageChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessMessage
import io.github.srgaabriel.deck.core.util.BlankStatelessMessageChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessServer
import io.github.srgaabriel.deck.core.util.BlankStatelessUser
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayChatMessageReactionCreatedEvent
import java.util.*

/**
 * Called when someone reacts to a [Message]
 * (not only when a new reaction emote is added to a message)
 */
public data class MessageReactionAddEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val serverId: GenericId?,
    val channelId: UUID,
    val messageId: UUID,
    val userId: GenericId,
    val emote: Emote
): DeckEvent {
    val server: StatelessServer? by lazy { serverId?.let { BlankStatelessServer(client, serverId) } }
    val channel: StatelessMessageChannel by lazy { BlankStatelessMessageChannel(client, channelId, serverId) }
    val message: StatelessMessage by lazy { BlankStatelessMessage(client, messageId, channelId, serverId) }
    val user: StatelessUser by lazy { BlankStatelessUser(client, userId) }
}

internal val EventService.messageReactionAdd: EventMapper<GatewayChatMessageReactionCreatedEvent, MessageReactionAddEvent> get() = mapper { client, event ->
    MessageReactionAddEvent(
        client = client,
        barebones = event,
        serverId = event.serverId.asNullable(),
        channelId = event.reaction.channelId,
        messageId = event.reaction.messageId,
        userId = event.reaction.createdBy,
        emote = Emote.from(event.reaction.emote)
    )
}