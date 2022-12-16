package io.github.srgaabriel.deck.core.stateless

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.core.entity.Documentation
import io.github.srgaabriel.deck.core.entity.impl.DeckDocumentation
import io.github.srgaabriel.deck.core.stateless.channel.StatelessDocumentationChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessDocumentationChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessServer
import io.github.srgaabriel.deck.rest.builder.CreateDocumentationRequestBuilder
import java.util.*

public interface StatelessDocumentation: StatelessEntity {
    public val id: IntGenericId
    public val channelId: UUID
    public val serverId: GenericId

    public val channel: StatelessDocumentationChannel get() = BlankStatelessDocumentationChannel(client, channelId, serverId)
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    /**
     * Updates **NOT PATCHES** this documentation with the data provided in the [builder]
     *
     * @param builder update builder
     *
     * @return updated documentation
     */
    public suspend fun update(builder: CreateDocumentationRequestBuilder.() -> Unit): Documentation =
        DeckDocumentation.from(client, client.rest.channel.updateDocumentation(channelId, id, builder))

    /**
     * Deletes this documentation
     */
    public suspend fun delete(): Unit =
        client.rest.channel.deleteDocumentation(channelId, id)

    public suspend fun getDocumentation(): Documentation =
        DeckDocumentation.from(client, client.rest.channel.getDocumentation(channelId, id))
}