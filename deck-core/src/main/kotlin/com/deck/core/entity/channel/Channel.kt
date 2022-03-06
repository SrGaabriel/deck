package com.deck.core.entity.channel

import com.deck.common.util.GenericId
import com.deck.core.entity.Entity
import com.deck.core.entity.misc.ChannelContentType
import com.deck.core.entity.misc.ChannelType
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.generic.GenericStatelessChannel
import com.deck.core.util.BlankStatelessUser
import kotlinx.datetime.Instant

public interface Channel : Entity, GenericStatelessChannel {
    public val name: String
    public val description: String

    public val type: ChannelType
    public val contentType: ChannelContentType

    public val createdAt: Instant
    public val archivedAt: Instant?

    public val creatorId: GenericId
    public val creator: StatelessUser get() = BlankStatelessUser(client, creatorId)

    public val archiverId: GenericId?
    public val archiver: StatelessUser? get() = archiverId?.let { BlankStatelessUser(client, it) }

    public val updatedAt: Instant?
    public val deletedAt: Instant?
}