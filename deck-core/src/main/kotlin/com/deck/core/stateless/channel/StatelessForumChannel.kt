package com.deck.core.stateless.channel

import com.deck.core.entity.channel.ForumChannel
import com.deck.core.entity.channel.ForumThread
import com.deck.core.stateless.StatelessEntity
import com.deck.core.stateless.StatelessTeam
import com.deck.rest.builder.CreateForumThreadBuilder
import java.util.*

public interface StatelessForumChannel: StatelessEntity<ForumChannel> {
    public val id: UUID
    public val team: StatelessTeam

    public suspend fun createThread(builder: CreateForumThreadBuilder.() -> Unit): ForumThread =
        client.entityDecoder.decodeForumThread(client.rest.channelRoute.createForumThread(id, builder))

    override suspend fun getState(): ForumChannel {
        return client.entityDelegator.getTeamChannel(id, team.id) as? ForumChannel
            ?: error("Tried to access invalid forum channel.")
    }
}