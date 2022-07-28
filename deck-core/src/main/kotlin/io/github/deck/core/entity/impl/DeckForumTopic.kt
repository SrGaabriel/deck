package io.github.deck.core.entity.impl

import io.github.deck.common.entity.RawForumTopic
import io.github.deck.common.util.GenericId
import io.github.deck.common.util.asNullable
import io.github.deck.common.util.mapToBuiltin
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.ForumTopic
import io.github.deck.core.entity.Mentions
import kotlinx.datetime.Instant
import java.util.*

public data class DeckForumTopic(
    override val client: DeckClient,
    override val id: Int,
    override val authorId: GenericId,
    override val serverId: GenericId,
    override val channelId: UUID,
    override val title: String,
    override val content: String,
    override val createdAt: Instant,
    override val updatedAt: Instant?,
    override val bumpedAt: Instant?,
    override val mentions: Mentions?
): ForumTopic {
    public companion object {
        public fun from(client: DeckClient, raw: RawForumTopic): DeckForumTopic = DeckForumTopic(
            client = client,
            id = raw.id,
            authorId = raw.createdBy,
            serverId = raw.serverId,
            channelId = raw.channelId.mapToBuiltin(),
            title = raw.title,
            content = raw.content,
            createdAt = raw.createdAt,
            updatedAt = raw.updatedAt.asNullable(),
            bumpedAt = raw.bumpedAt.asNullable(),
            mentions = raw.mentions.asNullable()?.let { Mentions.from(it) }
        )
    }
}