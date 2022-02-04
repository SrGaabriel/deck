package com.deck.core.stateless.channel

import com.deck.common.util.IntGenericId
import com.deck.core.entity.channel.ForumChannel
import com.deck.core.entity.channel.ForumPost
import com.deck.core.entity.impl.channel.DeckForumPost
import com.deck.core.stateless.StatelessEntity
import com.deck.core.stateless.StatelessTeam
import com.deck.core.util.BlankStatelessUser
import com.deck.rest.builder.CreateForumThreadReplyBuilder
import kotlinx.datetime.Clock

public interface StatelessForumPost: StatelessEntity<ForumChannel> {
    public val id: IntGenericId

    public val thread: StatelessForumThread
    public val team: StatelessTeam
    public val channel: StatelessForumChannel

    public suspend fun createReply(builder: CreateForumThreadReplyBuilder.() -> Unit): ForumPost {
        val replica = CreateForumThreadReplyBuilder().apply(builder)
        return DeckForumPost(
            client = client,
            id = client.rest.channelRoute.createForumThreadReply(channel.id, id, builder).replyId,
            content = replica.content,
            thread = thread,
            team = team,
            channel = channel,
            author = BlankStatelessUser(client, client.selfId),
            createdAt = Clock.System.now()
        )
    }

    public suspend fun addReaction(reactionId: IntGenericId): Unit =
        client.rest.channelRoute.addReactionToForumThreadReply(team.id, id, reactionId)

    public suspend fun removeReaction(reactionId: IntGenericId): Unit =
        client.rest.channelRoute.removeReactionFromForumThreadReply(team.id, id, reactionId)

    override suspend fun getState(): ForumChannel {
        TODO("Not yet implemented")
    }
}