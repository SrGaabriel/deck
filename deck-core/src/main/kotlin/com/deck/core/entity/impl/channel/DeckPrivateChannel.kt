package com.deck.core.entity.impl.channel

import com.deck.core.DeckClient
import com.deck.core.entity.channel.MessageChannel
import com.deck.core.entity.misc.ChannelContentType
import com.deck.core.entity.misc.ChannelType
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.StatelessUser
import kotlinx.datetime.Instant
import java.util.*

public data class DeckPrivateChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val name: String,
    override val description: String,
    override val type: ChannelType,
    override val contentType: ChannelContentType,
    override val createdAt: Instant,
    override val createdBy: StatelessUser,
    override val archivedAt: Instant?,
    override val archivedBy: StatelessUser?,
    override val updatedAt: Instant?,
    override val deletedAt: Instant?,
) : MessageChannel {
    override val team: StatelessTeam? = null
}