package com.deck.core.entity

import com.deck.common.util.GenericId
import com.deck.common.util.Timestamp
import com.deck.core.entity.misc.ChannelContentType
import com.deck.core.entity.misc.ChannelType
import java.util.*

interface Channel: Entity {
    val id: UUID
    val name: String
    val description: String

    val type: ChannelType
    val contentType: ChannelContentType

    val createdAt: Timestamp
    val createdBy: GenericId

    val archivedAt: Timestamp?
    val archivedBy: GenericId?

    val updatedAt: Timestamp?
    val deletedAt: Timestamp?
}
