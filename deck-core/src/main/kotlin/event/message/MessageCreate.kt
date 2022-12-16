package io.github.srgaabriel.deck.core.event.message

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.Message
import io.github.srgaabriel.deck.core.entity.impl.DeckMessage
import io.github.srgaabriel.deck.core.event.DeckEvent
import io.github.srgaabriel.deck.core.event.EventMapper
import io.github.srgaabriel.deck.core.event.EventService
import io.github.srgaabriel.deck.core.event.mapper
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.StatelessUser
import io.github.srgaabriel.deck.core.stateless.channel.StatelessMessageChannel
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayChatMessageCreatedEvent
import java.util.*

/**
 * Called when a [Message] is sent, be it on a private channel (DM)
 * or in a server channel
 */
public data class MessageCreateEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    public val message: Message
) : DeckEvent {
    inline val authorId: GenericId get() = message.authorId
    inline val channelId: UUID get() = message.channelId
    inline val serverId: GenericId? get() = message.serverId

    public val channel: StatelessMessageChannel by lazy { message.channel }
    public val author: StatelessUser by lazy { message.author }
    public val server: StatelessServer? by lazy { message.server }
}

internal val EventService.messageCreate: EventMapper<GatewayChatMessageCreatedEvent, MessageCreateEvent> get() = mapper { client, event ->
    MessageCreateEvent(
        client = client,
        barebones = event,
        message = DeckMessage.from(client, event.message)
    )
}