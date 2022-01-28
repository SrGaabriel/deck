package com.deck.core.stateless.channel

import com.deck.common.util.GenericId
import com.deck.core.entity.channel.ForumChannel
import com.deck.core.entity.channel.ForumThread
import com.deck.core.stateless.StatelessEntity
import com.deck.rest.builder.CreateForumThreadBuilder
import java.util.*

public interface StatelessForumChannel: StatelessEntity<ForumChannel> {
    public val id: UUID
    public val teamId: GenericId

    public suspend fun createThread(builder: CreateForumThreadBuilder.() -> Unit): ForumThread =
        client.entityDecoder.decodeForumThread(client.rest.channelRoute.createForumThread(id, builder))

    override suspend fun getState(): ForumChannel {
        return client.entityDelegator.getTeamChannel(id, teamId) as? ForumChannel
            ?: error("Tried to access invalid forum channel.")
    }
}