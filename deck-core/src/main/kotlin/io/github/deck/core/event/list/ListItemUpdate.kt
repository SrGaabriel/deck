package io.github.deck.core.event.list

import io.github.deck.core.DeckClient
import io.github.deck.core.entity.ListItem
import io.github.deck.core.entity.impl.DeckListItem
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.stateless.channel.StatelessListChannel
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayListItemUpdatedEvent

/**
 * Called when the attributes of a [ListItem] is edited
 * (not when it's marked as complete or incomplete)
 *
 * @see ListItemCompleteEvent
 * @see ListItemIncompleteEvent
 */
public data class ListItemUpdateEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val listItem: ListItem
): DeckEvent {
    val server: StatelessServer by lazy { listItem.server }
    val channel: StatelessListChannel by lazy { listItem.channel }
}

internal val EventService.listItemUpdate: EventMapper<GatewayListItemUpdatedEvent, ListItemUpdateEvent> get() = mapper { client, event ->
    ListItemUpdateEvent(
        client = client,
        barebones = event,
        listItem = DeckListItem.from(client, event.listItem)
    )
}