package com.deck.core.entity

import com.deck.common.util.GenericId
import com.deck.common.util.Timestamp
import com.deck.core.entity.misc.Content
import java.util.*

public interface Message : Entity {
    public val id: UUID
    public val content: Content

    public val teamId: GenericId?

    public val channelId: UUID
    public val repliesToId: UUID?

    public val createdAt: Timestamp
    public val updatedAt: Timestamp?

    public val createdBy: GenericId
    public val updatedBy: GenericId?

    public val isSilent: Boolean
    public val isPrivate: Boolean

    public suspend fun getChannel(): Channel
}
