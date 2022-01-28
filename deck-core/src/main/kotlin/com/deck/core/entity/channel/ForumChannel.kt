package com.deck.core.entity.channel

import com.deck.common.content.Content
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.DeckClient
import com.deck.core.entity.Entity
import com.deck.core.stateless.channel.StatelessForumChannel
import com.deck.core.stateless.channel.StatelessForumThread
import kotlinx.datetime.Instant

public interface ForumChannel: TeamChannel, StatelessForumChannel {
    override val teamId: GenericId
}

public interface ForumThread: Entity, StatelessForumThread {
    public val title: String
    public val content: Content

    public val createdAt: Instant
    public val createdBy: GenericId

    public val editedAt: Instant?

    public val isSticky: Boolean
    public val isShare: Boolean
    public val isLocked: Boolean
    public val isDeleted: Boolean
}

public class ForumThreadReply(
    public val client: DeckClient,
    public val id: IntGenericId,
    public val content: Content,
    public val thread: StatelessForumThread,
    public val teamId: GenericId,
    public val createdBy: GenericId
) {
    public suspend fun addReaction(reactionId: IntGenericId): Unit =
        client.rest.channelRoute.addReactionToForumThreadReply(teamId, id, reactionId)

    public suspend fun removeReaction(reactionId: IntGenericId): Unit =
        client.rest.channelRoute.removeReactionFromForumThreadReply(teamId, id, reactionId)
}