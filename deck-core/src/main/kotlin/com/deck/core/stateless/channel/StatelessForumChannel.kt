package com.deck.core.stateless.channel

import com.deck.core.entity.channel.ForumChannel
import com.deck.core.entity.channel.ForumThread
import com.deck.core.stateless.StatelessEntity
import com.deck.core.stateless.standard.StandardStatelessTeamChannel
import com.deck.rest.builder.CreateForumThreadBuilder

public interface StatelessForumChannel: StatelessEntity<ForumChannel>, StandardStatelessTeamChannel {
    /**
     * Creates a thread in this forum channel.
     *
     * @param builder thread builder
     * @return the created thread
     */
    public suspend fun createThread(builder: CreateForumThreadBuilder.() -> Unit): ForumThread =
        client.entityDecoder.decodeForumThread(client.rest.channelRoute.createForumThread(id, builder))

    override suspend fun getState(): ForumChannel {
        return client.entityDelegator.getTeamChannel(id, team.id) as? ForumChannel
            ?: error("Tried to access invalid forum channel.")
    }
}