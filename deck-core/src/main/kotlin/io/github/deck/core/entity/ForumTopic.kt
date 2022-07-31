package io.github.deck.core.entity

import io.github.deck.common.entity.RawForumTopicSummary
import io.github.deck.common.util.GenericId
import io.github.deck.common.util.IntGenericId
import io.github.deck.common.util.asNullable
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.channel.ForumChannel
import io.github.deck.core.stateless.StatelessForumTopic
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.util.BlankStatelessUser
import kotlinx.datetime.Instant
import java.util.*

/**
 * A topic (previously called ForumThread) from a [ForumChannel]
 */
public interface ForumTopic: StatelessForumTopic {
    /** This forum topic's title */
    public val title: String
    /** This forum topic's content */
    public val content: String

    /** The OP's (original poster) id */
    public val authorId: GenericId
    public val author: StatelessUser get() = BlankStatelessUser(client, authorId)

    /** When this forum topic was created */
    public val createdAt: Instant
    /** When this forum topic was last updated */
    public val updatedAt: Instant?
    /** Post's last activity time */
    public val bumpedAt: Instant?

    /** This forum topic's mentions, null if none */
    public val mentions: Mentions?

    /**
     * Creates a [ForumTopicSummary] from this [ForumTopic]
     */
    public fun asSummary(): ForumTopicSummary =
        ForumTopicSummary(client, id, channelId, serverId, title, authorId, createdAt, bumpedAt, updatedAt)
}

/**
 * Represents a partial forum topic. It's obtained when one tries to read
 * all forum topics from a [ForumChannel] at the same time.
 */
public data class ForumTopicSummary(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val channelId: UUID,
    override val serverId: GenericId,
    val title: String,
    val authorId: GenericId,
    val createdAt: Instant,
    val bumpedAt: Instant?,
    val updatedAt: Instant?
) : StatelessForumTopic {
    public companion object {
        public fun from(client: DeckClient, raw: RawForumTopicSummary): ForumTopicSummary =
            ForumTopicSummary(
                client = client,
                id = raw.id,
                channelId = raw.channelId,
                serverId = raw.serverId,
                title = raw.title,
                authorId = raw.createdBy,
                createdAt = raw.createdAt,
                bumpedAt = raw.bumpedAt.asNullable(),
                updatedAt = raw.updatedAt.asNullable()
            )
    }
}