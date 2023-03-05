package io.github.srgaabriel.deck.core.stateless.channel.content

import io.github.srgaabriel.deck.common.entity.ServerChannelType
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.core.entity.ForumTopicComment
import io.github.srgaabriel.deck.core.entity.impl.DeckForumTopicComment
import io.github.srgaabriel.deck.core.stateless.StatelessEntity
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.channel.StatelessForumChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessForumChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessForumTopic
import io.github.srgaabriel.deck.core.util.BlankStatelessServer
import io.github.srgaabriel.deck.core.util.ReactionHolder
import java.util.*

public interface StatelessForumTopicComment: StatelessEntity, ReactionHolder {
    public val id: IntGenericId
    public val forumTopicId: IntGenericId
    public val channelId: UUID
    public val serverId: GenericId

    public val forumTopic: StatelessForumTopic get() = BlankStatelessForumTopic(client, forumTopicId, channelId, serverId)
    public val channel: StatelessForumChannel get() = BlankStatelessForumChannel(client, channelId, serverId)
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    /**
     * Updates this comment with the new [content] provided
     *
     * @param content new content
     *
     * @return updated forum topic comment
     */
    public suspend fun update(content: String): ForumTopicComment =
        DeckForumTopicComment.from(client, serverId, client.rest.channel.updateForumTopicComment(channelId, forumTopicId, id, content))

    /**
     * Deletes this comment
     */
    public suspend fun delete(): Unit =
        client.rest.channel.deleteForumTopicComment(channelId, forumTopicId, id)

    override suspend fun addReaction(reactionId: IntGenericId): Unit =
        client.rest.channel.addReactionToComment(
            channelId,
            ServerChannelType.Forums,
            forumTopicId,
            id,
            reactionId
        )

    override suspend fun removeReaction(reactionId: IntGenericId): Unit =
        client.rest.channel.removeReactionFromComment(
            channelId,
            ServerChannelType.Forums,
            forumTopicId,
            id,
            reactionId
        )

    public suspend fun getForumTopicComment(): ForumTopicComment =
        DeckForumTopicComment.from(client, serverId, client.rest.channel.getForumTopicComment(channelId, forumTopicId, id))
}