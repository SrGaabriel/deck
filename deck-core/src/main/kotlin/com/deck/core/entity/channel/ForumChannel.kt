package com.deck.core.entity.channel

import com.deck.core.entity.Entity
import com.deck.core.stateless.StatelessServer
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessForumChannel
import kotlinx.datetime.Instant

public interface ForumThread: Entity {
    public val id: Int

    public val server: StatelessServer
    public val channel: StatelessForumChannel

    public val title: String
    public val content: String

    public val createdAt: Instant
    public val createdBy: StatelessUser

    public val updatedAt: Instant
}