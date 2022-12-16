package io.github.srgaabriel.deck.core.entity.impl

import io.github.srgaabriel.deck.common.entity.RawForumTopicComment
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.common.util.asNullable
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.ForumTopicComment
import io.github.srgaabriel.deck.core.entity.Mentions
import kotlinx.datetime.Instant
import java.util.*

public data class DeckForumTopicComment(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val forumTopicId: IntGenericId,
    override val channelId: UUID,
    override val serverId: GenericId,
    override val content: String,
    override val mentions: Mentions?,
    override val createdAt: Instant,
    override val updatedAt: Instant?,
): ForumTopicComment {
    public companion object {
        public fun from(client: DeckClient, serverId: GenericId, raw: RawForumTopicComment): DeckForumTopicComment =
            DeckForumTopicComment(
                client,
                raw.id,
                raw.forumTopicId,
                raw.channelId,
                serverId,
                raw.content,
                raw.mentions.asNullable()?.let { Mentions.from(it) },
                raw.createdAt,
                raw.updatedAt.asNullable()
            )
    }
}