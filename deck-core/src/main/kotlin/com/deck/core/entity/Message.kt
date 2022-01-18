package com.deck.core.entity

import com.deck.common.util.GenericId
import com.deck.common.util.Timestamp
import com.deck.core.entity.misc.Content
import java.util.*

interface Message: Entity {
    val id: UUID
    val content: Content

    val teamId: GenericId?

    val channelId: UUID
    val repliesToId: UUID?

    val createdAt: Timestamp
    val updatedAt: Timestamp?

    val createdBy: GenericId
    val updatedBy: GenericId?

    val isSilent: Boolean
    val isPrivate: Boolean

    suspend fun getChannel(): Channel
}
