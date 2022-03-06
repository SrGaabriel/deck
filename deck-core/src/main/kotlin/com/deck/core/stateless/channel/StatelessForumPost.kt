package com.deck.core.stateless.channel

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.entity.channel.ForumPost
import com.deck.core.entity.impl.channel.DeckForumPost
import com.deck.core.stateless.StatelessEntity
import com.deck.rest.builder.CreateForumThreadReplyBuilder
import kotlinx.datetime.Clock
import java.util.*

public interface StatelessForumPost: StatelessEntity<ForumPost> {
    public val id: IntGenericId

    public val threadId: IntGenericId
    public val teamId: GenericId
    public val channelId: UUID

    /**
     * Creates a reply to this post.
     *
     * @param builder post builder
     * @return the created post
     */
    public suspend fun createReply(builder: CreateForumThreadReplyBuilder.() -> Unit): ForumPost {
        val replica = CreateForumThreadReplyBuilder().apply(builder)
        return DeckForumPost(
            client = client,
            id = client.rest.channelRoute.createForumThreadReply(channelId, threadId, builder).replyId,
            content = replica.content,
            threadId = threadId,
            channelId = channelId,
            authorId = client.selfId,
            teamId = teamId,
            createdAt = Clock.System.now()
        )
    }

    /**
     * Adds a reaction to this post.
     *
     * @param reactionId reaction id
     */
    public suspend fun addReaction(reactionId: IntGenericId): Unit =
        client.rest.channelRoute.addReactionToForumThreadReply(teamId, id, reactionId)

    /**
     * Removes a reaction from the post. You can only remove reactions
     * added by yourself/your account.
     *
     * @param reactionId reaction id
     */
    public suspend fun removeReaction(reactionId: IntGenericId): Unit =
        client.rest.channelRoute.removeReactionFromForumThreadReply(teamId, id, reactionId)

    override suspend fun getState(): ForumPost {
        return (if (this.id == threadId)
            client.entityDelegator.getForumChannelThread(threadId, channelId)?.originalPost
        else client.entityDelegator.getForumChannelReply(id, threadId, teamId, channelId))
            ?: error("Tried to access an invalid forum reply state.")
    }
}