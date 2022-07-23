package io.github.deck.core.event.message

import io.github.deck.core.DeckClient
import io.github.deck.core.entity.Message
import io.github.deck.core.entity.impl.DeckMessage
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.stateless.channel.StatelessMessageChannel
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayChatMessageCreatedEvent

/**
 * Called when a [Message] is sent, be it on a private channel (DM)
 * or in a server channel
 */
public data class MessageCreateEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    public val message: Message
) : DeckEvent {
    val channel: StatelessMessageChannel get() = message.channel
    val author: StatelessUser get() = message.author
    val server: StatelessServer? get() = message.server
}

internal val EventService.messageCreateEvent: EventMapper<GatewayChatMessageCreatedEvent, MessageCreateEvent> get() = mapper { client, event ->
    MessageCreateEvent(
        client = client,
        barebones = event,
        message = DeckMessage.from(client, event.message)
    )
}