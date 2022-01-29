package com.deck.gateway.entity

import com.deck.common.util.GenericId
import com.deck.common.util.UniqueId
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
public data class RawPartialDeletedMessage(
    public val id: UniqueId,
    public val serverId: GenericId,
    public val channelId: UniqueId,
    public val deletedAt: Instant
)