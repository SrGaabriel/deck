package io.github.deck.gateway.entity

import io.github.deck.common.util.GenericId
import io.github.deck.common.util.UniqueId
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
public data class RawPartialDeletedMessage(
    public val id: UniqueId,
    public val serverId: GenericId,
    public val channelId: UniqueId,
    public val deletedAt: Instant
)