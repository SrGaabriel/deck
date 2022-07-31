package io.github.deck.core.entity.impl

import io.github.deck.common.entity.RawWebhook
import io.github.deck.common.util.GenericId
import io.github.deck.common.util.asNullable
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.Webhook
import kotlinx.datetime.Instant
import java.util.*

public data class DeckWebhook(
    override val client: DeckClient,
    override val id: UUID,
    override val serverId: GenericId,
    override val name: String,
    override val channelId: UUID,
    override val createdAt: Instant,
    override val deletedAt: Instant?,
    override val creatorId: GenericId,
    override val token: String?
) : Webhook {
    public companion object {
        public fun from(client: DeckClient, raw: RawWebhook): DeckWebhook = DeckWebhook(
            client = client,
            id = raw.id,
            serverId = raw.serverId,
            name = raw.name,
            channelId = raw.channelId,
            createdAt = raw.createdAt,
            deletedAt = raw.deletedAt.asNullable(),
            creatorId = raw.createdBy,
            token = raw.token.asNullable()
        )
    }
}