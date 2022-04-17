package io.github.deck.core.stateless.channel

import io.github.deck.common.util.GenericId
import io.github.deck.common.util.IntGenericId
import io.github.deck.core.entity.Documentation
import io.github.deck.core.entity.impl.DeckDocumentation
import io.github.deck.core.stateless.StatelessEntity
import io.github.deck.rest.builder.CreateDocumentationRequestBuilder
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