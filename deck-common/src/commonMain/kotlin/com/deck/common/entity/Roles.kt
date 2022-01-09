package com.deck.common.entity

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.LongGenericId
import com.deck.common.util.Timestamp
import kotlinx.serialization.Serializable

@Serializable
data class RawRole(
    val id: IntGenericId,
    val color: String,
    val isBase: Boolean,
    val isDisplayedSeparately: Boolean,
    val isMentionable: Boolean,
    val isSelfAssignable: Boolean,
    val name: String,
    val createdAt: Timestamp,
    val discordRoleId: LongGenericId? = null,
    val discordSyncedAt: Timestamp? = null,
    val priority: Int,
    val botScope: Boolean? = null,
    val teamId: GenericId,
    val updatedAt: Timestamp,
    val permissions: RawRolePermissions
)

@Serializable
data class RawRolePermissions(
    val announcements: Int,
    val bots: Int,
    val brackets: Int,
    val calendar: Int,
    val chat: Int,
    val customization: Int,
    val docs: Int,
    val forms: Int,
    val forums: Int,
    val general: Int,
    val lists: Int,
    val matchmaking: Int,
    val media: Int,
    val recruitment: Int,
    val scheduling: Int,
    val streams: Int,
    val voice: Int,
    val xp: Int
)