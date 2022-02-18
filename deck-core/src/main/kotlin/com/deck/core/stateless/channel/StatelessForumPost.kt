package com.deck.core.stateless.channel

import com.deck.common.util.IntGenericId
import com.deck.core.entity.channel.ForumPost
import com.deck.core.entity.impl.channel.DeckForumPost
import com.deck.core.stateless.StatelessEntity
import com.deck.core.stateless.StatelessTeam
import com.deck.core.util.BlankStatelessUser
import com.deck.rest.builder.CreateForumThreadReplyBuilder
import kotlinx.datetime.Clock

public interface StatelessForumPost: StatelessEntity<ForumPost> {
    public val id: IntGenericId

    public val thread: StatelessForumThread
    public val team: StatelessTeam
    public val channel: StatelessForumChannel

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
            id = client.rest.channelRoute.createForumThreadReply(channel.id, thread.id, builder).replyId,
            content = replica.content,
            thread = thread,
            team = team,
            channel = channel,
            author = BlankStatelessUser(client, client.selfId),
            createdAt = Clock.System.now()
        )
    }

    /**
     * Adds a reaction to this post.
     *
     * @param reactionId reaction id
     */
    public suspend fun addReaction(reactionId: IntGenericId): Unit =
        client.rest.channelRoute.addReactionToForumThreadReply(team.id, id, reactionId)

    /**
     * Removes a reaction from the post. You can only remove reactions
     * added by yourself/your account.
     *
     * @param reactionId reaction id
     */
    public suspend fun removeReaction(reactionId: IntGenericId): Unit =
        client.rest.channelRoute.removeReactionFromForumThreadReply(team.id, id, reactionId)

    override suspend fun getState(): ForumPost {
        return if (this.id == thread.id)
            thread.getState().originalPost
        else client.entityDelegator.getForumChannelReply(id, thread.id, channel.id)
            ?: error("Tried to access an invalid forum reply state.")
    }
}