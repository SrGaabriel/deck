package io.github.deck.core.stateless.channel

import io.github.deck.common.util.IntGenericId
import io.github.deck.core.entity.Documentation
import io.github.deck.core.entity.impl.DeckDocumentation
import io.github.deck.rest.builder.CreateDocumentationRequestBuilder

public interface StatelessDocumentationChannel: StatelessServerChannel {
    /**
     * Creates a [Documentation] within this documentation channel.
     *
     * @param builder documentation builder
     * @return the created documentation
     */
    public suspend fun createDocumentation(builder: CreateDocumentationRequestBuilder.() -> Unit): Documentation =
        DeckDocumentation.from(client, client.rest.channel.createDocumentation(id, builder))

    /**
     * Retrieves the [Documentation] associated with the specified [documentationId]
     *
     * @param documentationId documentation's id
     * @return found documentation
     */
    public suspend fun getDocumentation(documentationId: IntGenericId): Documentation =
        DeckDocumentation.from(client, client.rest.channel.getDocumentation(id, documentationId))

    /**
     * Retrieves all [Documentation]s within this documentation channel
     *
     * @return list of [Documentation]
     */
    public suspend fun getDocumentations(): List<Documentation> =
        client.rest.channel.getDocumentations(id).map { DeckDocumentation.from(client, it) }

    /**
     * Updates **(NOT PATCHES)** the documentation associated with the provided [documentationId]
     *
     * @param documentationId documentation's id
     * @param builder update builder
     *
     * @return updated documentation
     */
    public suspend fun updateDocumentation(documentationId: IntGenericId, builder: CreateDocumentationRequestBuilder.() -> Unit): Documentation =
        DeckDocumentation.from(client, client.rest.channel.updateDocumentation(id, documentationId, builder))

    /**
     * Deletes the documentation associated with the provided [documentationId]
     *
     * @param documentationId documentation's id
     */
    public suspend fun deleteDocumentation(documentationId: IntGenericId): Unit =
        client.rest.channel.deleteDocumentation(id, documentationId)
}