package com.deck.core.stateless.channel

import com.deck.common.util.GenericId
import com.deck.core.entity.channel.ForumThread
import com.deck.core.stateless.StatelessEntity
import com.deck.core.stateless.StatelessServer
import com.deck.core.util.BlankStatelessServer
import com.deck.rest.builder.CreateForumThreadRequestBuilder
import java.util.*

public interface StatelessForumChannel: StatelessEntity {
    public val id: UUID
    public val serverId: GenericId

    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    public suspend fun createThread(builder: CreateForumThreadRequestBuilder.() -> Unit): ForumThread =
        client.entityDecoder.decodeForumThread(
            client.rest.channelRoute.createForumThread(id, builder)
        )
}