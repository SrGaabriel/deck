package com.deck.core.entity.impl

import com.deck.common.entity.RawWebhook
import com.deck.common.util.GenericId
import com.deck.common.util.asNullable
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.Webhook
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
            id = raw.id.mapToBuiltin(),
            serverId = raw.serverId,
            name = raw.name,
            channelId = raw.channelId.mapToBuiltin(),
            createdAt = raw.createdAt,
            deletedAt = raw.deletedAt.asNullable(),
            creatorId = raw.createdBy,
            token = raw.token.asNullable()
        )
    }
}