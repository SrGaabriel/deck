package com.deck.core.entity.impl.channel

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.DeckClient
import com.deck.core.entity.channel.ScheduleAvailability
import com.deck.core.entity.channel.SchedulingChannel
import com.deck.core.entity.misc.ChannelContentType
import com.deck.core.entity.misc.ChannelType
import kotlinx.datetime.Instant
import java.util.*

public data class DeckSchedulingChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val name: String,
    override val description: String,
    override val type: ChannelType,
    override val contentType: ChannelContentType,
    override val createdAt: Instant,
    override val archivedAt: Instant?,
    override val creatorId: GenericId,
    override val archiverId: GenericId?,
    override val updatedAt: Instant?,
    override val deletedAt: Instant?,
    override val teamId: GenericId
): SchedulingChannel

public data class DeckScheduleAvailability(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val teamId: GenericId,
    override val channelId: UUID
): ScheduleAvailability