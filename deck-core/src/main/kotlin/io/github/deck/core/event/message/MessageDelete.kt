package io.github.deck.core.event.message

import io.github.deck.common.util.GenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.Message
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.stateless.channel.StatelessMessageChannel
import io.github.deck.core.util.BlankStatelessMessageChannel
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayChatMessageDeletedEvent
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