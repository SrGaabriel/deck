@file:UseSerializers(UUIDSerializer::class)

package io.github.srgaabriel.deck.gateway.entity

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.UUIDSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.*

@Serializable
public data class RawPartialDeletedMessage(
    public val id: UUID,
    public val serverId: GenericId,
    public val channelId: UUID,
    public val deletedAt: Instant
)