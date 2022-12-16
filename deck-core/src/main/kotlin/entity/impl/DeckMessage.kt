package io.github.srgaabriel.deck.core.entity.impl

import io.github.srgaabriel.deck.common.Embed
import io.github.srgaabriel.deck.common.entity.RawMessage
import io.github.srgaabriel.deck.common.from
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.asNullable
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.Mentions
import io.github.srgaabriel.deck.core.entity.Message
import kotlinx.datetime.Instant
import java.util.*

public data class DeckMessage(
    override val client: DeckClient,
    override val id: UUID,
    override val content: String,
    override val authorId: GenericId,
    override val serverId: GenericId?,
    override val channelId: UUID,
    override val embeds: List<Embed>,
    override val createdAt: Instant,
    override val updatedAt: Instant?,
    override val mentions: Mentions?,
    override val repliesTo: List<UUID>,
    override val isPrivate: Boolean,
    override val isSilent: Boolean,
    override val createdByWebhookId: UUID?
): Message {
    public companion object {
        public fun from(client: DeckClient, raw: RawMessage): DeckMessage = DeckMessage(
            client = client,
            id = raw.id,
            content = raw.content.asNullable().orEmpty(),
            authorId = raw.createdBy,
            serverId = raw.serverId.asNullable(),
            channelId = raw.channelId,
            embeds = raw.embeds.map { Embed.from(it) },
            createdAt = raw.createdAt,
            updatedAt = raw.updatedAt.asNullable(),
            mentions = raw.mentions.asNullable()?.let { Mentions.from(it) },
            repliesTo = raw.replyMessageIds.asNullable()?.map { it }.orEmpty(),
            isPrivate = raw.isPrivate,
            isSilent = raw.isSilent,
            createdByWebhookId = raw.createdByWebhookId.asNullable()
        )
    }
}