package com.deck.core.stateless

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.entity.Documentation
import com.deck.core.stateless.channel.StatelessDocumentationChannel
import com.deck.core.util.BlankStatelessDocumentationChannel
import com.deck.core.util.BlankStatelessServer
import com.deck.rest.builder.CreateDocumentationRequestBuilder
import java.util.*

public interface StatelessDocumentation: StatelessEntity {
    public val id: IntGenericId
    public val channelId: UUID
    public val serverId: GenericId

    public val channel: StatelessDocumentationChannel get() = BlankStatelessDocumentationChannel(client, channelId, serverId)
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    public suspend fun update(builder: CreateDocumentationRequestBuilder.() -> Unit): Documentation =
        client.entityDecoder.decodeDocumentation(
            client.rest.channelRoute.updateDocumentation(channel.id, id, builder)
        )

    public suspend fun delete(): Unit =
        client.rest.channelRoute.deleteDocumentation(channel.id, id)
}