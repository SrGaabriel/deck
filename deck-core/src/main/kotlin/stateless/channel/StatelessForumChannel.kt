package io.github.srgaabriel.deck.core.stateless.channel

import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.core.entity.ForumTopic
import io.github.srgaabriel.deck.core.entity.ForumTopicComment
import io.github.srgaabriel.deck.core.entity.channel.ForumChannel
import io.github.srgaabriel.deck.core.entity.impl.DeckForumTopic
import io.github.srgaabriel.deck.core.entity.impl.DeckForumTopicComment
import io.github.srgaabriel.deck.core.util.getChannelOf
import io.github.srgaabriel.deck.rest.builder.CreateForumTopicRequestBuilder
import io.github.srgaabriel.deck.rest.builder.UpdateForumTopicRequestBuilder

public interface StatelessForumChannel: StatelessServerChannel {
    /**
     * Creates a new forum topic within this channel
     *
     * @param builder topic builder
     * @return created forum topic
     */
    public suspend fun createTopic(builder: CreateForumTopicRequestBuilder.() -> Unit): ForumTopic =
        DeckForumTopic.from(client, client.rest.channel.createForumTopic(id, builder))

    /**
     * Patches the forum topic with the provided id ([forumTopicId]) in this channel
     *
     * @param forumTopicId forum topic id
     * @param builder update builder
     *
     * @return updated forum topic
     */
    public suspend fun patchTopic(forumTopicId: IntGenericId, builder: UpdateForumTopicRequestBuilder.() -> Unit): ForumTopic =
        DeckForumTopic.from(client, client.rest.channel.updateForumTopic(id, forumTopicId, builder))

    /**
     * Deletes the forum topic with the provided id ([forumTopicId]) from this channel
     *
     * @param forumTopicId forum topic id
     */
    public suspend fun deleteTopic(forumTopicId: IntGenericId): Unit =
        client.rest.channel.deleteForumTopic(id, forumTopicId)

    /**
     * Posts a comment under the topic associated with the provided [topicId]
     *
     * @param topicId topic where the comment is meant to be posted under
     * @param content comment's content
     *
     * @return the created comment
     */
    public suspend fun createTopicComment(topicId: IntGenericId, content: String): ForumTopicComment =
        DeckForumTopicComment.from(client, serverId, client.rest.channel.createForumTopicComment(id, topicId, content))

    /**
     * Retrieves the comment associated with the provided [topicId] and [commentId]
     *
     * @param topicId topic where the comment is situated
     * @param commentId comment id
     *
     * @return the found comment
     */
    public suspend fun getTopicComment(topicId: IntGenericId, commentId: IntGenericId): ForumTopicComment =
        DeckForumTopicComment.from(client, serverId, client.rest.channel.getForumTopicComment(id, topicId, commentId))

    /**
     * Retrieves all comments posted under the topic associated with the provided [topicId]
     *
     * @param topicId topic to read the comments from
     *
     * @return all comments in the topic in question
     */
    public suspend fun getTopicComments(topicId: IntGenericId): List<ForumTopicComment> =
        client.rest.channel.getForumTopicComments(id, topicId).map { DeckForumTopicComment.from(client, serverId, it) }

    /**
     * Deletes the comment associated with the provided [topicId] and [commentId]
     *
     * @param topicId topic where the comment is situated
     * @param commentId comment id
     */
    public suspend fun deleteTopicComment(topicId: IntGenericId, commentId: IntGenericId): Unit =
        client.rest.channel.deleteForumTopicComment(id, topicId, commentId)

    override suspend fun getChannel(): ForumChannel =
        client.getChannelOf(id)
}