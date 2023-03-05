package io.github.srgaabriel.deck.core.entity.impl

import io.github.srgaabriel.deck.common.entity.RawDocumentationComment
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.common.util.asNullable
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.DocumentationComment
import io.github.srgaabriel.deck.core.entity.Mentions
import kotlinx.datetime.Instant
import java.util.*

public data class DeckDocumentationComment(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val channelId: UUID,
    override val documentationId: IntGenericId,
    override val serverId: GenericId,
    override val content: String,
    override val authorId: GenericId,
    override val createdAt: Instant,
    override val updatedAt: Instant?,
    override val mentions: Mentions?,
): DocumentationComment {
    public companion object {
        public fun from(client: DeckClient, serverId: GenericId, raw: RawDocumentationComment): DeckDocumentationComment = DeckDocumentationComment(
            client = client,
            id = raw.id,
            channelId = raw.channelId,
            documentationId = raw.docId,
            serverId = serverId,
            content = raw.content,
            authorId = raw.createdBy,
            createdAt = raw.createdAt,
            updatedAt = raw.updatedAt.asNullable(),
            mentions = raw.mentions.asNullable()?.let { Mentions.from(it) }
        )
    }
}