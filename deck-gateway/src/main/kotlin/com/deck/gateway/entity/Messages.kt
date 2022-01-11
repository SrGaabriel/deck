package com.deck.gateway.entity

import com.deck.common.entity.RawMessageContent
import com.deck.common.util.GenericId
import com.deck.common.util.OptionalProperty
import com.deck.common.util.Timestamp
import com.deck.common.util.UniqueId
import kotlinx.serialization.Serializable

/**
 * Parameters [isSilent] [isPrivate] [repliesToIds] aren't present in webhook messages
 */
@Serializable
data class RawPartialReceivedMessage(
    val id: UniqueId,
    val createdBy: GenericId,
    val content: RawMessageContent,
    val repliesToIds: OptionalProperty<List<UniqueId>> = OptionalProperty.NotPresent,
    val repliesTo: UniqueId?,
    val type: String,
    val createdAt: Timestamp,
    val isSilent: OptionalProperty<Boolean> = OptionalProperty.NotPresent,
    val isPrivate: OptionalProperty<Boolean> = OptionalProperty.NotPresent
)

@Serializable
data class RawPartialRepliedMessage(
    val id: UniqueId,
    val content: RawMessageContent,
    val type: String,
    val createdBy: GenericId,
    val createdAt: Timestamp,
    val editedAt: Timestamp?,
    val deletedAt: Timestamp?,
    val channelId: UniqueId,
    val webhookId: GenericId?,
    val isSilent: Boolean,
    val isPrivate: Boolean
)

@Serializable
data class RawPartialDeletedMessage(
    val id: UniqueId,
    val deletedAt: Timestamp,
    val createdAt: Timestamp
)