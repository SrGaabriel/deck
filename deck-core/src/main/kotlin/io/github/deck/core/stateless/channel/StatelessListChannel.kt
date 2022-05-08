package io.github.deck.core.stateless.channel

import io.github.deck.common.util.GenericId
import io.github.deck.core.entity.ListItem
import io.github.deck.core.entity.impl.DeckListItem
import io.github.deck.core.stateless.StatelessEntity
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.rest.builder.CreateListItemRequestBuilder
import io.github.deck.rest.builder.UpdateListItemRequestBuilder
import java.util.*

public interface StatelessListChannel: StatelessEntity {
    public val id: UUID
    public val serverId: GenericId

    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    public suspend fun createItem(builder: CreateListItemRequestBuilder.() -> Unit): ListItem =
        DeckListItem.from(client, client.rest.channel.createListItem(id, builder))

    public suspend fun completeItem(itemId: UUID): Unit =
        client.rest.channel.completeListItem(id, itemId)

    public suspend fun uncompleteItem(itemId: UUID): Unit =
        client.rest.channel.completeListItem(id, itemId)

    public suspend fun getItem(itemId: UUID): ListItem =
        DeckListItem.from(client, client.rest.channel.retrieveListItem(id, itemId))

    public suspend fun getItems(): List<ListItem> =
        client.rest.channel.retrieveListChannelItems(id).map { DeckListItem.from(client, it) }

    public suspend fun updateItem(itemId: UUID, builder: UpdateListItemRequestBuilder.() -> Unit): ListItem =
        DeckListItem.from(client, client.rest.channel.updateListItem(id, itemId, builder))

    public suspend fun deleteItem(itemId: UUID): Unit =
        client.rest.channel.deleteListItem(id, itemId)
}