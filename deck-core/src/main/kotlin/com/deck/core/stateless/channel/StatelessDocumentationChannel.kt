package com.deck.core.stateless.channel

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.entity.Documentation
import com.deck.core.stateless.StatelessEntity
import com.deck.rest.builder.CreateDocumentationRequestBuilder
import java.util.*

public interface StatelessDocumentationChannel: StatelessEntity {
    public val id: UUID
    public val serverId: GenericId

    public suspend fun createDocumentation(builder: CreateDocumentationRequestBuilder.() -> Unit): Documentation =
        client.entityDecoder.decodeDocumentation(
            client.rest.channelRoute.createDocumentation(id, builder)
        )

    public suspend fun getDocumentation(documentationId: IntGenericId): Documentation =
        client.entityDecoder.decodeDocumentation(
            client.rest.channelRoute.getDocumentation(id, documentationId)
        )

    public suspend fun getDocumentations(): List<Documentation> =
        client.rest.channelRoute.getDocumentations(id).map(client.entityDecoder::decodeDocumentation)
}