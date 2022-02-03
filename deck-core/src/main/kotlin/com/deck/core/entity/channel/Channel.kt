package com.deck.core.entity.channel

import com.deck.core.entity.Entity
import com.deck.core.entity.misc.ChannelContentType
import com.deck.core.entity.misc.ChannelType
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.StatelessUser
import kotlinx.datetime.Instant
import java.util.*

public interface Channel : Entity {
    public val id: UUID
    public val name: String
    public val description: String

    public val team: StatelessTeam?

    public val type: ChannelType
    public val contentType: ChannelContentType

    public val createdAt: Instant
    public val createdBy: StatelessUser

    public val archivedAt: Instant?
    public val archivedBy: StatelessUser?

    public val updatedAt: Instant?
    public val deletedAt: Instant?
}