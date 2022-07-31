package io.github.deck.core.event.message

import io.github.deck.common.util.Emote
import io.github.deck.common.util.GenericId
import io.github.deck.common.util.asNullable
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
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayChatMessageReactionDeletedEvent
import java.util.*

/**
 * Called when someone removes a reaction from a [Message]
 * (not only when a reaction emote is removed completely from a message)
 */
public data class MessageReactionRemoveEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
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

internal val EventService.messageReactionRemoveEvent: EventMapper<GatewayChatMessageReactionDeletedEvent, MessageReactionRemoveEvent> get() = mapper { client, event ->
    MessageReactionRemoveEvent(
        client = client,
        barebones = event,
        serverId = event.serverId.asNullable(),
        channelId = event.reaction.channelId,
        messageId = event.reaction.messageId,
        userId = event.reaction.createdBy,
        emote = Emote.from(event.reaction.emote)
    )
}