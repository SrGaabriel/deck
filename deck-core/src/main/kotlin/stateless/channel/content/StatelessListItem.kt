package io.github.srgaabriel.deck.core.stateless.channel.content

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.core.entity.ListItem
import io.github.srgaabriel.deck.core.entity.impl.DeckListItem
import io.github.srgaabriel.deck.core.stateless.StatelessEntity
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.channel.StatelessListChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessListChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessServer
import io.github.srgaabriel.deck.rest.builder.CreateListItemRequestBuilder
import io.github.srgaabriel.deck.rest.builder.UpdateListItemRequestBuilder
import java.util.*

public interface StatelessListItem: StatelessEntity {
    public val id: UUID
    public val serverId: GenericId
    public val channelId: UUID

    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)
    public val channel: StatelessListChannel get() = BlankStatelessListChannel(client, id, serverId)

    /**
     * Overwrites this list item with the new data provided in the [builder].
     *
     * @param builder item builder, requires a [CreateListItemRequestBuilder.label] and a [CreateListItemRequestBuilder.note]
     * @return updated item containing new data
     */
    public suspend fun update(builder: UpdateListItemRequestBuilder.() -> Unit): ListItem =
        DeckListItem.from(client, client.rest.channel.updateListItem(channelId, id, builder))

    /**
     * Marks this list item as 'completed' within this channel. _(not to be confused with [delete])_
     */
    public suspend fun complete(): Unit =
        client.rest.channel.completeListItem(channelId, id)

    /**
     * Marks this list item as 'uncompleted' within this channel.
     */
    public suspend fun uncomplete(): Unit =
        client.rest.channel.uncompleteListItem(channelId, id)

    /**
     * Deletes this list item. _(not to be confused with [complete])_
     */
    public suspend fun delete(): Unit =
        client.rest.channel.deleteListItem(channelId, id)

    public suspend fun getListItem(): ListItem =
        DeckListItem.from(client, client.rest.channel.getListItem(channelId, id))
}