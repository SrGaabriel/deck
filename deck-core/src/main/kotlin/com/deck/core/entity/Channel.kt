package com.deck.core.entity

import com.deck.common.util.GenericId
import com.deck.core.entity.misc.ChannelContentType
import com.deck.core.entity.misc.ChannelType
import com.deck.core.stateless.StatelessMessageChannel
import kotlinx.datetime.Instant

public interface Channel : Entity, StatelessMessageChannel {
    public val name: String
    public val description: String

    public val type: ChannelType
    public val contentType: ChannelContentType

    public val createdAt: Instant
    public val createdBy: GenericId

    public val archivedAt: Instant?
    public val archivedBy: GenericId?

    public val updatedAt: Instant?
    public val deletedAt: Instant?

    override val teamId: GenericId?
}