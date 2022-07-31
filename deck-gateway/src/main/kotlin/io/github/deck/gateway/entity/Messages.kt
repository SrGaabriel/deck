@file:UseSerializers(UUIDSerializer::class)

package io.github.deck.gateway.entity

import io.github.deck.common.entity.RawEmote
import io.github.deck.common.util.GenericId
import io.github.deck.common.util.UUIDSerializer
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

@Serializable
public data class RawUpdatedMessageReaction(
    val channelId: UUID,
    val messageId: UUID,
    val createdBy: GenericId,
    val emote: RawEmote
)