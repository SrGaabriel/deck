package com.deck.core.stateless

import com.deck.common.util.GenericId
import com.deck.core.entity.ListItem
import com.deck.core.entity.impl.DeckListItem
import com.deck.core.stateless.channel.StatelessListChannel
import com.deck.core.util.BlankStatelessListChannel
import com.deck.core.util.BlankStatelessServer
import com.deck.rest.builder.CreateListItemRequestBuilder
import com.deck.rest.builder.UpdateListItemRequestBuilder
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
     * Deletes this item
     */
    public suspend fun delete(): Unit =
        client.rest.channel.deleteListItem(channelId, id)
}