package com.deck.core.entity.channel

import com.deck.common.content.Content
import com.deck.common.util.GenericId
import com.deck.core.entity.Entity
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessForumChannel
import com.deck.core.stateless.channel.StatelessForumPost
import com.deck.core.stateless.channel.StatelessForumThread
import com.deck.core.util.BlankStatelessUser
import kotlinx.datetime.Instant

public interface ForumChannel : TeamChannel, StatelessForumChannel

public interface ForumThread: Entity, StatelessForumThread {
    public val title: String

    public val originalPost: ForumPost

    public val authorId: GenericId
    public val author: StatelessUser get() = BlankStatelessUser(client, authorId)

    public val createdAt: Instant
    public val editedAt: Instant?

    public val isSticky: Boolean
    public val isShare: Boolean
    public val isLocked: Boolean
    public val isDeleted: Boolean
}

public interface ForumPost: Entity, StatelessForumPost {
    public val content: Content

    public val authorId: GenericId
    public val author: StatelessUser get() = BlankStatelessUser(client, authorId)

    public val createdAt: Instant
}