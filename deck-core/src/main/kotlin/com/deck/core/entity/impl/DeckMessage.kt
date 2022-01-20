package com.deck.core.entity.impl

import com.deck.common.content.Content
import com.deck.common.util.GenericId
import com.deck.common.util.Timestamp
import com.deck.core.DeckClient
import com.deck.core.entity.Channel
import com.deck.core.entity.Message
import com.deck.core.stateless.StatelessMessageChannel
import java.util.*

public data class DeckMessage(
    override val client: DeckClient,
    override val id: UUID,
    override val content: Content,
    override val teamId: GenericId?,
    override val channel: StatelessMessageChannel,
    override val repliesToId: UUID?,
    override val createdAt: Timestamp,
    override val updatedAt: Timestamp?,
    override val createdBy: GenericId,
    override val updatedBy: GenericId?,
    override val isSilent: Boolean,
    override val isPrivate: Boolean
) : Message {
    override suspend fun getChannel(): Channel = channel.getState()
}
