package com.deck.core.entity.channel

import com.deck.common.content.Content
import com.deck.core.entity.Entity
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessForumChannel
import com.deck.core.stateless.channel.StatelessForumPost
import com.deck.core.stateless.channel.StatelessForumThread
import kotlinx.datetime.Instant

public interface ForumChannel : TeamChannel, StatelessForumChannel

public interface ForumThread: Entity, StatelessForumThread {
    public val title: String

    public val originalPost: ForumPost
    public val author: StatelessUser

    public val createdAt: Instant
    public val editedAt: Instant?

    public val isSticky: Boolean
    public val isShare: Boolean
    public val isLocked: Boolean
    public val isDeleted: Boolean
}

public interface ForumPost: Entity, StatelessForumPost {
    public val content: Content
    public val author: StatelessUser

    public val createdAt: Instant
}