package com.deck.core.stateless.channel

import com.deck.common.util.GenericId
import com.deck.core.entity.ListItem
import com.deck.core.stateless.StatelessEntity
import com.deck.core.stateless.StatelessServer
import com.deck.core.util.BlankStatelessServer
import com.deck.rest.builder.CreateListItemRequestBuilder
import java.util.*

public interface StatelessListChannel: StatelessEntity {
    public val id: UUID
    public val serverId: GenericId

    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    public suspend fun createItem(builder: CreateListItemRequestBuilder.() -> Unit): ListItem =
        client.entityDecoder.decodeListItem(
            client.rest.channel.createListItem(id, builder)
        )
}