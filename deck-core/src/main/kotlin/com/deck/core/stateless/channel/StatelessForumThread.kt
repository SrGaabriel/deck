package com.deck.core.stateless.channel

import com.deck.common.util.IntGenericId
import com.deck.core.entity.channel.ForumThread
import com.deck.core.entity.channel.ForumThreadReply
import com.deck.core.stateless.StatelessEntity
import com.deck.core.stateless.StatelessTeam
import com.deck.rest.builder.CreateForumThreadBuilder
import com.deck.rest.builder.CreateForumThreadReplyBuilder

public interface StatelessForumThread: StatelessEntity<ForumThread> {
    public val id: IntGenericId
    public val team: StatelessTeam
    public val channel: StatelessForumChannel

    public suspend fun createReply(builder: CreateForumThreadReplyBuilder.() -> Unit): ForumThreadReply {
        val replica = CreateForumThreadReplyBuilder().apply(builder)
        return ForumThreadReply(
            client = client,
            id = client.rest.channelRoute.createForumThreadReply(channel.id, id, builder).replyId,
            content = replica.content,
            thread = this,
            team = team,
            createdBy = client.selfId
        )
    }

    public suspend fun update(builder: CreateForumThreadBuilder.() -> Unit): Unit =
        client.rest.channelRoute.updateForumThread(channel.id, id, builder)

    public suspend fun delete(): Unit =
        client.rest.channelRoute.deleteForumThread(channel.id, id)

    override suspend fun getState(): ForumThread {
        TODO("Not yet implemented")
    }
}