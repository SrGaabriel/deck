package io.github.deck.core.event.list

import io.github.deck.common.util.GenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.ListItem
import io.github.deck.core.entity.impl.DeckListItem
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.stateless.channel.StatelessListChannel
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.gateway.event.Payload
import io.github.deck.gateway.event.type.GatewayListItemCreatedEvent

/**
 * Called when a new [ListItem] is created
 */
public data class ListItemCreateEvent(
    override val client: DeckClient,
    override val payload: Payload,
    val serverId: GenericId,
    val listItem: ListItem
): DeckEvent {
    val server: StatelessServer get() = BlankStatelessServer(client, serverId)
    val channel: StatelessListChannel get() = listItem.channel
}

internal val EventService.listItemCreateEvent: EventMapper<GatewayListItemCreatedEvent, ListItemCreateEvent>
    get() = mapper { client, event ->
    ListItemCreateEvent(
        client = client,
        payload = event.payload,
        serverId = event.serverId,
        listItem = DeckListItem.from(client, event.listItem)
    )
}