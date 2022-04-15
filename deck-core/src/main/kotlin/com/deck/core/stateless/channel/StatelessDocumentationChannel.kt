package com.deck.core.stateless.channel

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.entity.Documentation
import com.deck.core.entity.impl.DeckDocumentation
import com.deck.core.stateless.StatelessEntity
import com.deck.rest.builder.CreateDocumentationRequestBuilder
import java.util.*

public interface StatelessDocumentationChannel: StatelessEntity {
    public val id: UUID
    public val serverId: GenericId

    public suspend fun createDocumentation(builder: CreateDocumentationRequestBuilder.() -> Unit): Documentation =
        DeckDocumentation.from(client, client.rest.channel.createDocumentation(id, builder))

    public suspend fun getDocumentation(documentationId: IntGenericId): Documentation =
        DeckDocumentation.from(client, client.rest.channel.getDocumentation(id, documentationId))

    public suspend fun getDocumentations(): List<Documentation> =
        client.rest.channel.getDocumentations(id).map { DeckDocumentation.from(client, it) }

    public suspend fun updateDocumentation(documentationId: IntGenericId, builder: CreateDocumentationRequestBuilder.() -> Unit): Documentation =
        DeckDocumentation.from(client, client.rest.channel.updateDocumentation(id, documentationId, builder))

    public suspend fun deleteDocumentation(documentationId: IntGenericId): Unit =
        client.rest.channel.deleteDocumentation(id, documentationId)
}