package io.github.deck.core.event.channel

import io.github.deck.common.util.GenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.channel.ServerChannel
import io.github.deck.core.entity.impl.DeckServerChannel
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayServerChannelUpdatedEvent

/**
 * Called when a [ServerChannel] is edited
 */
public data class ServerChannelUpdateEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val serverId: GenericId,
    val channel: ServerChannel
): DeckEvent {
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)
}

internal val EventService.serverChannelUpdateEvent: EventMapper<GatewayServerChannelUpdatedEvent, ServerChannelUpdateEvent> get() = mapper { client, event ->
    ServerChannelUpdateEvent(
        client = client,
        barebones = event,
        serverId = event.serverId,
        channel = DeckServerChannel.from(client, event.channel)
    )
}