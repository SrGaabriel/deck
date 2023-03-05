package io.github.srgaabriel.deck.core.stateless.channel.content

import io.github.srgaabriel.deck.common.entity.ServerChannelType
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.core.entity.CalendarEventComment
import io.github.srgaabriel.deck.core.entity.Documentation
import io.github.srgaabriel.deck.core.entity.DocumentationComment
import io.github.srgaabriel.deck.core.entity.impl.DeckCalendarEventComment
import io.github.srgaabriel.deck.core.entity.impl.DeckDocumentation
import io.github.srgaabriel.deck.core.entity.impl.DeckDocumentationComment
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.channel.StatelessDocumentationChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessDocumentationChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessServer
import io.github.srgaabriel.deck.rest.builder.CreateDocumentationRequestBuilder

public interface StatelessDocumentation: StatelessChannelContent {
    public val serverId: GenericId
    override val channelType: ServerChannelType get() = ServerChannelType.Documentation

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

    /**
     * Posts a comment under this doc
     *
     * @param content comment's content
     *
     * @return the created comment
     */
    public suspend fun createComment(content: String): CalendarEventComment =
        DeckCalendarEventComment.from(client, serverId, client.rest.channel.createCalendarEventComment(channelId, id, content))

    /**
     * Retrieves the comment associated with the provided [commentId]
     *
     * @param commentId comment id
     *
     * @return the found comment
     */
    public suspend fun getComment(commentId: IntGenericId): DocumentationComment =
        DeckDocumentationComment.from(client, serverId, client.rest.channel.getDocumentationComment(channelId, id, commentId))

    /**
     * Retrieves all comments posted under this doc
     *
     * @return all comments under this doc
     */
    public suspend fun getComments(): List<DocumentationComment> =
        client.rest.channel.getDocumentationComments(channelId, id).map { DeckDocumentationComment.from(client, serverId, it) }

    /**
     * Deletes the comment associated with the provided [commentId]
     *
     * @param commentId comment id
     */
    public suspend fun deleteComment(commentId: IntGenericId): Unit =
        client.rest.channel.deleteDocumentationComment(channelId, id, commentId)

    public suspend fun getDocumentation(): Documentation =
        DeckDocumentation.from(client, client.rest.channel.getDocumentation(channelId, id))
}