package com.deck.core.entity.impl.channel

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.entity.channel.MessageChannel
import com.deck.core.entity.channel.TeamChannel
import com.deck.core.entity.misc.ChannelContentType
import com.deck.core.entity.misc.ChannelType
import kotlinx.datetime.Instant
import java.util.*

public class DeckTeamMessageChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val name: String,
    override val description: String,
    override val teamId: GenericId,
    override val type: ChannelType,
    override val contentType: ChannelContentType,
    override val createdAt: Instant,
    override val createdBy: GenericId,
    override val archivedAt: Instant?,
    override val archivedBy: GenericId?,
    override val updatedAt: Instant?,
    override val deletedAt: Instant?
): MessageChannel, TeamChannel