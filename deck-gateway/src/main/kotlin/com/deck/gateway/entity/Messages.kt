package com.deck.gateway.entity

import com.deck.common.entity.RawMessageContent
import com.deck.common.util.GenericId
import com.deck.common.util.OptionalProperty
import com.deck.common.util.UniqueId
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

/**
 * Parameters [isSilent] [isPrivate] [repliesToIds] aren't present in webhook messages
 */
@Serializable
public data class RawPartialReceivedMessage(
    val id: UniqueId,
    val createdBy: GenericId,
    val content: RawMessageContent,
    val repliesToIds: OptionalProperty<List<UniqueId>> = OptionalProperty.NotPresent,
    val repliesTo: UniqueId?,
    val type: String,
    val createdAt: Instant,
    val editedAt: OptionalProperty<Instant?> = OptionalProperty.NotPresent,
    val isSilent: OptionalProperty<Boolean> = OptionalProperty.NotPresent,
    val isPrivate: OptionalProperty<Boolean> = OptionalProperty.NotPresent
)

@Serializable
public data class RawPartialEditedMessage(
    val id: UniqueId,
    val createdBy: GenericId,
    val content: RawMessageContent,
    val repliesToIds: List<UniqueId>?,
    val type: String,
    val createdAt: Instant,
    val editedAt: Instant,
    val isPrivate: Boolean
)

@Serializable
public data class RawPartialDeletedMessage(
    val id: UniqueId,
    val deletedAt: Instant,
    val createdAt: Instant
)

@Serializable
public data class RawMessageIdObject(
    val id: UniqueId
)