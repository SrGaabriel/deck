package com.deck.core.stateless.channel

import com.deck.core.entity.ListItem
import com.deck.core.stateless.StatelessEntity
import com.deck.core.stateless.StatelessServer
import com.deck.rest.builder.CreateListItemRequestBuilder
import java.util.*

public interface StatelessListChannel: StatelessEntity {
    public val id: UUID
    public val server: StatelessServer?

    public suspend fun createItem(builder: CreateListItemRequestBuilder.() -> Unit): ListItem =
        client.entityDecoder.decodeListItem(
            client.rest.channelRoute.createListItem(id, builder)
        )
}