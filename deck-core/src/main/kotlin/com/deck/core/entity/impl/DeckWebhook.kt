package com.deck.core.entity.impl

import com.deck.common.util.GenericId
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
) : Webhook