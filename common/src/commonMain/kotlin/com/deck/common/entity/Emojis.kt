package com.deck.common.entity

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.LongGenericId
import com.deck.common.util.Timestamp
import kotlinx.serialization.Serializable

@Serializable
data class RawEmoji(
    val id: IntGenericId,
    val name: String,
    val createdBy: GenericId,
    val createdAt: Timestamp,
    val png: String,
    val webp: String,
    val apng: String,
    val aliases: List<String>,
    val teamId: GenericId,
    val isDeleted: Boolean,
    val discordEmojiId: LongGenericId?,
    val discordSyncedAt: Timestamp?
)

@Serializable
data class RawReaction(
    val id: IntGenericId,
    val customReaction: RawCustomReaction,
    val customReactionId: IntGenericId
)

@Serializable
data class RawCustomReaction(
    val id: Int,
    val name: String,
    val png: String,
    val webp: String,
    val apgn: String
)