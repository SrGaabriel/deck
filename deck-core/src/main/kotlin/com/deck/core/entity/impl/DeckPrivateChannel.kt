package com.deck.core.entity.impl

import com.deck.common.util.GenericId
import kotlinx.datetime.Instant
import com.deck.core.DeckClient
import com.deck.core.entity.Channel
import com.deck.core.entity.misc.ChannelContentType
import com.deck.core.entity.misc.ChannelType
import java.util.*

public data class DeckPrivateChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val name: String,
    override val description: String,
    override val type: ChannelType,
    override val contentType: ChannelContentType,
    override val createdAt: Instant,
    override val createdBy: GenericId,
    override val archivedAt: Instant?,
    override val archivedBy: GenericId?,
    override val updatedAt: Instant?,
    override val deletedAt: Instant?,
) : Channel {
    override val teamId: GenericId? get() = null
}
