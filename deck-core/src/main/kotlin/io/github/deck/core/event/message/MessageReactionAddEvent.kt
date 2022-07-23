package io.github.deck.core.event.message

import io.github.deck.common.util.Emote
import io.github.deck.common.util.GenericId
import io.github.deck.common.util.asNullable
import io.github.deck.common.util.mapToBuiltin
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.Message
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessMessage
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.stateless.channel.StatelessMessageChannel
import io.github.deck.core.util.BlankStatelessMessage
import io.github.deck.core.util.BlankStatelessMessageChannel
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.core.util.BlankStatelessUser
import io.github.deck.gateway.event.Payload
import io.github.deck.gateway.event.type.GatewayChatMessageReactionCreatedEvent
import java.util.*

/**
 * Called when someone reacts to a [Message]
 * (not only when a new reaction emote is added to a message)
 */
public data class MessageReactionAddEvent(
    override val client: DeckClient,
    override val payload: Payload,
    val serverId: GenericId?,
    val channelId: UUID,
    val messageId: UUID,
    val userId: GenericId,
    val emote: Emote
): DeckEvent {
    val server: StatelessServer? get() = serverId?.let { BlankStatelessServer(client, serverId) }
    val channel: StatelessMessageChannel get() = BlankStatelessMessageChannel(client, channelId, serverId)
    val message: StatelessMessage get() = BlankStatelessMessage(client, messageId, channelId, serverId)
    val user: StatelessUser get() = BlankStatelessUser(client, userId)
}

internal val EventService.messageReactionAddEvent: EventMapper<GatewayChatMessageReactionCreatedEvent, MessageReactionAddEvent> get() = mapper { client, event ->
    MessageReactionAddEvent(
        client = client,
        payload = event.payload,
        serverId = event.serverId.asNullable(),
        channelId = event.reaction.channelId.mapToBuiltin(),
        messageId = event.reaction.messageId.mapToBuiltin(),
        userId = event.reaction.createdBy,
        emote = Emote.from(event.reaction.emote)
    )
}