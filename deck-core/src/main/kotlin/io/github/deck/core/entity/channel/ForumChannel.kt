package io.github.deck.core.entity.channel

import io.github.deck.common.util.GenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.stateless.channel.StatelessForumChannel
import io.github.deck.core.util.BlankStatelessForumChannel
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.core.util.BlankStatelessUser
import kotlinx.datetime.Instant
import java.util.*

public interface ForumThread {
    public val client: DeckClient
    public val id: Int

    public val authorId: GenericId
    public val serverId: GenericId
    public val channelId: UUID

    public val author: StatelessUser get() = BlankStatelessUser(client, authorId)
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)
    public val channel: StatelessForumChannel get() = BlankStatelessForumChannel(client, channelId, serverId)

    public val title: String
    public val content: String

    public val createdAt: Instant
    public val updatedAt: Instant?
}