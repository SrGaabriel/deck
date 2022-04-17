package io.github.deck.core.stateless.channel

import io.github.deck.common.util.GenericId
import io.github.deck.core.entity.channel.ForumThread
import io.github.deck.core.entity.impl.DeckForumThread
import io.github.deck.core.stateless.StatelessEntity
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.rest.builder.CreateForumThreadRequestBuilder
import java.util.*

public interface StatelessForumChannel: StatelessEntity {
    public val id: UUID
    public val serverId: GenericId

    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    public suspend fun createThread(builder: CreateForumThreadRequestBuilder.() -> Unit): ForumThread =
        DeckForumThread.from(client, client.rest.channel.createForumThread(id, builder))
}