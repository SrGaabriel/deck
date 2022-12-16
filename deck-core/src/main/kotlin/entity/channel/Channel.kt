package io.github.srgaabriel.deck.core.entity.channel

import io.github.srgaabriel.deck.common.entity.RawServerChannelType
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.core.stateless.StatelessUser
import io.github.srgaabriel.deck.core.stateless.channel.StatelessChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessUser
import kotlinx.datetime.Instant

public interface Channel: StatelessChannel {
    public val name: String
    public val topic: String?

    public val type: RawServerChannelType
    public val isPublic: Boolean

    public val createdAt: Instant
    public val archivedAt: Instant?

    public val creatorId: GenericId
    public val creator: StatelessUser get() = BlankStatelessUser(client, creatorId)

    public val archiverId: GenericId?
    public val archiver: StatelessUser? get() = archiverId?.let { BlankStatelessUser(client, it) }

    public val updatedAt: Instant?
}