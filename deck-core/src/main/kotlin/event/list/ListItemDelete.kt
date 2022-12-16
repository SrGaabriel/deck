package io.github.srgaabriel.deck.core.event.list

import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.ListItem
import io.github.srgaabriel.deck.core.entity.impl.DeckListItem
import io.github.srgaabriel.deck.core.event.DeckEvent
import io.github.srgaabriel.deck.core.event.EventMapper
import io.github.srgaabriel.deck.core.event.EventService
import io.github.srgaabriel.deck.core.event.mapper
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.channel.StatelessListChannel
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayListItemDeletedEvent

/**
 * Called when a [ListItem] is deleted (not completed)
 */
public data class ListItemDeleteEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val listItem: ListItem
): DeckEvent {
    val server: StatelessServer by lazy { listItem.server }
    val channel: StatelessListChannel by lazy { listItem.channel }
}

internal val EventService.listItemDelete: EventMapper<GatewayListItemDeletedEvent, ListItemDeleteEvent>
    get() = mapper { client, event ->
        ListItemDeleteEvent(
        client = client,
        barebones = event,
        listItem = DeckListItem.from(client, event.listItem)
    )
}