package com.deck.core.entity.impl

import com.deck.common.content.Content
import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.entity.Message
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.channel.StatelessMessageChannel
import kotlinx.datetime.Instant
import java.util.*

public data class DeckMessage(
    override val client: DeckClient,
    override val id: UUID,
    override val content: Content,
    override val team: StatelessTeam?,
    override val channel: StatelessMessageChannel,
    override val repliesToId: UUID?,
    override val createdAt: Instant,
    override val updatedAt: Instant?,
    override val createdBy: GenericId,
    override val updatedBy: GenericId?,
    override val isSilent: Boolean,
    override val isPrivate: Boolean
) : Message