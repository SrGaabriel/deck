package com.deck.core.entity

import com.deck.common.content.Content
import com.deck.common.util.GenericId
import com.deck.common.util.Timestamp
import com.deck.core.stateless.StatelessMessage
import java.util.*

public interface Message : Entity, StatelessMessage {
    public val content: Content

    public val teamId: GenericId?

    public val repliesToId: UUID?

    public val createdAt: Timestamp
    public val updatedAt: Timestamp?

    public val createdBy: GenericId
    public val updatedBy: GenericId?

    public val isSilent: Boolean
    public val isPrivate: Boolean

    public suspend fun getChannel(): Channel
}
