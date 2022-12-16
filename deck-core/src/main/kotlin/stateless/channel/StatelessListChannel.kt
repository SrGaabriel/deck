package io.github.srgaabriel.deck.core.stateless.channel

import io.github.srgaabriel.deck.core.entity.ListItem
import io.github.srgaabriel.deck.core.entity.channel.ListChannel
import io.github.srgaabriel.deck.core.entity.impl.DeckListItem
import io.github.srgaabriel.deck.core.event.list.ListItemCompleteEvent
import io.github.srgaabriel.deck.core.event.list.ListItemIncompleteEvent
import io.github.srgaabriel.deck.core.util.getChannelOf
import io.github.srgaabriel.deck.rest.builder.CreateListItemRequestBuilder
import io.github.srgaabriel.deck.rest.builder.UpdateListItemRequestBuilder
import java.util.*

public interface StatelessListChannel: StatelessServerChannel {
    /**
     * Creates a new [ListItem] within this list channel.
     *
     * @param builder list item builder
     * @return created list item
     */
    public suspend fun createItem(builder: CreateListItemRequestBuilder.() -> Unit): ListItem =
        DeckListItem.from(client, client.rest.channel.createListItem(id, builder))

    /**
     * Marks the list item associated with the provided [itemId] as completed, **without deleting it**.
     *
     * If this is called in an already complete list item, it'll not throw any errors but instead
     * fire a new [ListItemCompleteEvent] event.
     *
     * @param itemId list item id
     */
    public suspend fun completeItem(itemId: UUID): Unit =
        client.rest.channel.completeListItem(id, itemId)

    /**
     * Marks the list item associated with the provided [itemId] as uncompleted.
     *
     * If this is called in an already uncomplete list item, it'll not throw any errors but instead
     * fire a new [ListItemIncompleteEvent] event.
     *
     * @param itemId list item id
     */
    public suspend fun uncompleteItem(itemId: UUID): Unit =
        client.rest.channel.completeListItem(id, itemId)

    /**
     * Retrieves the [ListItem] in this channel associated with the provided [itemId] id.
     *
     * @param itemId list item id
     * @return found list item
     */
    public suspend fun getItem(itemId: UUID): ListItem =
        DeckListItem.from(client, client.rest.channel.getListItem(id, itemId))

    /**
     * Retrieves all list items within this list channel
     *
     * @return found list items
     */
    public suspend fun getItems(): List<ListItem> =
        client.rest.channel.getListChannelItems(id).map { DeckListItem.from(client, it) }

    /**
     * Updates **(NOT PATCHES)** the list item associated with the provided [itemId].
     *
     * @param itemId list item id
     * @param builder update builder
     *
     * @return new list item
     */
    public suspend fun updateItem(itemId: UUID, builder: UpdateListItemRequestBuilder.() -> Unit): ListItem =
        DeckListItem.from(client, client.rest.channel.updateListItem(id, itemId, builder))

    /**
     * Deletes the list item associated with the provided [itemId]. _(not to be confused with [completeItem])_
     *
     * @param itemId list item id
     */
    public suspend fun deleteItem(itemId: UUID): Unit =
        client.rest.channel.deleteListItem(id, itemId)

    override suspend fun getChannel(): ListChannel =
        client.getChannelOf(id)
}