package com.deck.core.stateless.channel

import com.deck.common.util.IntGenericId
import com.deck.core.entity.channel.ForumThread
import com.deck.core.stateless.StatelessEntity
import com.deck.core.stateless.StatelessTeam
import com.deck.rest.builder.CreateForumThreadBuilder

public interface StatelessForumThread: StatelessEntity<ForumThread> {
    public val id: IntGenericId
    public val team: StatelessTeam
    public val channel: StatelessForumChannel

    /**
     * Updates your forum thread.
     *
     * @param builder update builder
     */
    public suspend fun update(builder: CreateForumThreadBuilder.() -> Unit): Unit =
        client.rest.channelRoute.updateForumThread(channel.id, id, builder)

    /**
     * Deletes your forum thread.
     */
    public suspend fun delete(): Unit =
        client.rest.channelRoute.deleteForumThread(channel.id, id)

    override suspend fun getState(): ForumThread {
        TODO("Not yet implemented")
    }
}