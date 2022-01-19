package com.deck.core.entity

import com.deck.common.util.GenericId
import com.deck.common.util.Timestamp
import com.deck.core.entity.misc.ChannelContentType
import com.deck.core.entity.misc.ChannelType
import java.util.*

public interface Channel : Entity {
    public val id: UUID
    public val name: String?
    public val description: String?

    public val type: ChannelType
    public val contentType: ChannelContentType

    public val createdAt: Timestamp
    public val createdBy: GenericId

    public val archivedAt: Timestamp?
    public val archivedBy: GenericId?

    public val updatedAt: Timestamp?
    public val deletedAt: Timestamp?
}
