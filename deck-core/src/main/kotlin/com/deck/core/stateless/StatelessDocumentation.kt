package com.deck.core.stateless

import com.deck.common.util.IntGenericId
import com.deck.core.entity.Documentation
import com.deck.core.stateless.channel.StatelessDocumentationChannel
import com.deck.rest.builder.CreateDocumentationRequestBuilder

public interface StatelessDocumentation: StatelessEntity {
    public val id: IntGenericId
    public val channel: StatelessDocumentationChannel

    public suspend fun update(builder: CreateDocumentationRequestBuilder.() -> Unit): Documentation =
        client.entityDecoder.decodeDocumentation(
            client.rest.channelRoute.updateDocumentation(channel.id, id, builder)
        )

    public suspend fun delete(): Unit =
        client.rest.channelRoute.deleteDocumentation(channel.id, id)
}