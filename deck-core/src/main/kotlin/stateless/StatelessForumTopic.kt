package io.github.srgaabriel.deck.core.stateless

import io.github.srgaabriel.deck.common.util.Emote
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.core.entity.ForumTopic
import io.github.srgaabriel.deck.core.entity.ForumTopicComment
import io.github.srgaabriel.deck.core.entity.impl.DeckForumTopic
import io.github.srgaabriel.deck.core.entity.impl.DeckForumTopicComment
import io.github.srgaabriel.deck.core.stateless.channel.StatelessForumChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessForumChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessServer
import io.github.srgaabriel.deck.core.util.ReactionHolder
import io.github.srgaabriel.deck.rest.builder.UpdateForumTopicRequestBuilder
import java.util.*

public interface StatelessForumTopic: StatelessEntity, ReactionHolder {
    public val id: IntGenericId
    public val channelId: UUID
    public val serverId: GenericId

    public val channel: StatelessForumChannel get() = BlankStatelessForumChannel(client, channelId, serverId)
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    /**
     * **Patches** this forum topic
     *
     * @param builder update builder
     *
     * @return updated forum topic
     */
    public suspend fun patch(builder: UpdateForumTopicRequestBuilder.() -> Unit): ForumTopic =
        DeckForumTopic.from(client, client.rest.channel.updateForumTopic(channelId, id, builder))

    /**
     * Deletes this forum topic
     */
    public suspend fun delete(): Unit =
        client.rest.channel.deleteForumTopic(channelId, id)

    /**
     * Pins this forum topic
     */
    public suspend fun pin(): Unit =
        client.rest.channel.pinForumTopic(channelId, id)

    /**
     * Unpins this forum topic
     */
    public suspend fun unpin(): Unit =
        client.rest.channel.unpinForumTopic(channelId, id)

    /**
     * Locks this forum topic
     */
    public suspend fun lock(): Unit =
        client.rest.channel.lockForumTopic(channelId, id)

    /**
     * Unlocks this forum topic
     */
    public suspend fun unlock(): Unit =
        client.rest.channel.unlockForumTopic(channelId, id)

    /**
     * Posts a comment under this topic
     *
     * @param content comment's content
     *
     * @return the created comment
     */
    public suspend fun createComment(content: String): ForumTopicComment =
        DeckForumTopicComment.from(client, serverId, client.rest.channel.createForumTopicComment(channelId, id, content))

    /**
     * Retrieves the comment associated with the provided [commentId]
     *
     * @param commentId comment id
     *
     * @return the found comment
     */
    public suspend fun getComment(commentId: IntGenericId): ForumTopicComment =
        DeckForumTopicComment.from(client, serverId, client.rest.channel.getForumTopicComment(channelId, id, commentId))

    /**
     * Retrieves all comments posted under this topic
     *
     * @return all comments in this topic
     */
    public suspend fun getComments(): List<ForumTopicComment> =
        client.rest.channel.getForumTopicComments(channelId, id).map { DeckForumTopicComment.from(client, serverId, it) }

    /**
     * Deletes the comment associated with the provided [commentId]
     *
     * @param commentId comment id
     */
    public suspend fun deleteComment(commentId: IntGenericId): Unit =
        client.rest.channel.deleteForumTopicComment(channelId, id, commentId)

    override suspend fun addReaction(reactionId: IntGenericId): Unit =
        client.rest.channel.addReactionToForumTopic(channelId, id, reactionId)

    override suspend fun removeReaction(reactionId: IntGenericId): Unit =
        client.rest.channel.removeReactionFromForumTopic(channelId, id, reactionId)

    public suspend fun addReactionToComment(commentId: IntGenericId, reactionId: IntGenericId): Unit =
        client.rest.channel.addReactionToForumTopicComment(channelId, id, commentId, reactionId)

    public suspend fun addReactionToComment(commentId: IntGenericId, emote: Emote): Unit =
        addReactionToComment(commentId, emote.id)

    public suspend fun removeReactionFromComment(commentId: IntGenericId, emote: Emote): Unit =
        addReactionToComment(commentId, emote.id)

    public suspend fun removeReactionFromComment(commentId: IntGenericId, reactionId: IntGenericId): Unit =
        client.rest.channel.removeReactionFromForumTopicComment(channelId, id, commentId, reactionId)

    public suspend fun getForumTopic(): ForumTopic =
        DeckForumTopic.from(client, client.rest.channel.getForumTopic(channelId, id))
}