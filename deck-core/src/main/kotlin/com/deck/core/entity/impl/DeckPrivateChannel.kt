package com.deck.core.entity.impl

import com.deck.common.util.GenericId
import com.deck.common.util.Timestamp
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
    override val createdAt: Timestamp,
    override val createdBy: GenericId,
    override val archivedAt: Timestamp?,
    override val archivedBy: GenericId?,
    override val updatedAt: Timestamp?,
    override val deletedAt: Timestamp?,
) : Channel
