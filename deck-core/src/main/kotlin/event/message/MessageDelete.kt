package io.github.srgaabriel.deck.core.event.message

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.Message
import io.github.srgaabriel.deck.core.event.DeckEvent
import io.github.srgaabriel.deck.core.event.EventMapper
import io.github.srgaabriel.deck.core.event.EventService
import io.github.srgaabriel.deck.core.event.mapper
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.channel.StatelessMessageChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessMessageChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessServer
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayChatMessageDeletedEvent
import kotlinx.datetime.Instant
import java.util.*

/**
 * Called when a [Message] is deleted, be it on a private channel (DM)
 * or in a server channel
 */
public data class MessageDeleteEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    public val messageId: UUID,
    public val channelId: UUID,
    public val serverId: GenericId?,
    public val deletedAt: Instant
): DeckEvent {
    public val channel: StatelessMessageChannel by lazy { BlankStatelessMessageChannel(client, channelId, serverId) }
    public val server: StatelessServer? by lazy { serverId?.let { BlankStatelessServer(client, it) } }
}

internal val EventService.messageDelete: EventMapper<GatewayChatMessageDeletedEvent, MessageDeleteEvent> get() = mapper { client, event ->
    MessageDeleteEvent(
        client = client,
        barebones = event,
        messageId = event.message.id,
        serverId = event.serverId,
        channelId = event.message.channelId,
        deletedAt = event.message.deletedAt
    )
}