package io.github.srgaabriel.deck.core.stateless.channel.content

import io.github.srgaabriel.deck.common.entity.ServerChannelType
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.core.entity.DocumentationComment
import io.github.srgaabriel.deck.core.entity.impl.DeckDocumentationComment
import io.github.srgaabriel.deck.core.stateless.StatelessEntity
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.channel.StatelessDocumentationChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessDocumentation
import io.github.srgaabriel.deck.core.util.BlankStatelessDocumentationChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessServer
import io.github.srgaabriel.deck.core.util.ReactionHolder
import java.util.*

public interface StatelessDocumentationComment: StatelessEntity, ReactionHolder {
    public val id: IntGenericId
    public val channelId: UUID
    public val documentationId: IntGenericId
    public val serverId: GenericId

    public val documentation: StatelessDocumentation get() = BlankStatelessDocumentation(client, documentationId, channelId, serverId)
    public val channel: StatelessDocumentationChannel get() = BlankStatelessDocumentationChannel(client, channelId, serverId)
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    /**
     * Updates this comment with the new [content] provided
     *
     * @param content new content
     *
     * @return updated documentation comment
     */
    public suspend fun update(content: String): DocumentationComment =
        DeckDocumentationComment.from(client, serverId, client.rest.channel.updateDocumentationComment(channelId, documentationId, id, content))

    /**
     * Deletes this comment
     */
    public suspend fun delete(): Unit =
        client.rest.channel.deleteDocumentationComment(channelId, documentationId, id)

    override suspend fun addReaction(reactionId: IntGenericId): Unit =
        client.rest.channel.addReactionToComment(
            channelId,
            ServerChannelType.Documentation,
            documentationId,
            id,
            reactionId
        )

    override suspend fun removeReaction(reactionId: IntGenericId): Unit =
        client.rest.channel.removeReactionFromComment(
            channelId,
            ServerChannelType.Documentation,
            documentationId,
            id,
            reactionId
        )

    public suspend fun getDocumentationComment(): DocumentationComment =
        DeckDocumentationComment.from(client, serverId, client.rest.channel.getDocumentationComment(channelId, documentationId, id))
}