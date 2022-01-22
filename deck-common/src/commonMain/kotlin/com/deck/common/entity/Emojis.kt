package com.deck.common.entity

import com.deck.common.util.*
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
public data class RawEmoji(
    val id: IntGenericId,
    val name: String,
    val createdBy: GenericId,
    val createdAt: Instant,
    val png: String,
    val webp: String,
    val apng: String,
    val aliases: List<String>,
    val teamId: GenericId,
    val isDeleted: Boolean,
    val discordEmojiId: LongGenericId?,
    val discordSyncedAt: Instant?
)

@Serializable
public data class RawReaction(
    val id: OptionalProperty<IntGenericId> = OptionalProperty.NotPresent,
    val customReaction: OptionalProperty<RawCustomReaction> = OptionalProperty.NotPresent,
    val customReactionId: IntGenericId
)

@Serializable
public data class RawCustomReaction(
    val id: IntGenericId,
    val name: String,
    val png: String,
    val webp: String?,
    val apgn: OptionalProperty<String?> = OptionalProperty.NotPresent
)
