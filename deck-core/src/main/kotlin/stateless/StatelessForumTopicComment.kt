package io.github.srgaabriel.deck.core.stateless

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.core.entity.ForumTopicComment
import io.github.srgaabriel.deck.core.entity.impl.DeckForumTopicComment
import io.github.srgaabriel.deck.core.stateless.channel.StatelessForumChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessForumChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessForumTopic
import io.github.srgaabriel.deck.core.util.BlankStatelessServer
import java.util.*

public interface StatelessForumTopicComment: StatelessEntity {
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
     * @return updated forum topic
     */
    public suspend fun update(content: String): ForumTopicComment =
        DeckForumTopicComment.from(client, serverId, client.rest.channel.updateForumTopicComment(channelId, forumTopicId, id, content))

    /**
     * Deletes this comment
     */
    public suspend fun delete(): Unit =
        client.rest.channel.deleteForumTopicComment(channelId, forumTopicId, id)

    public suspend fun getForumTopicComment(): ForumTopicComment =
        DeckForumTopicComment.from(client, serverId, client.rest.channel.getForumTopicComment(channelId, forumTopicId, id))
}