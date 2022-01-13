package com.deck.common.entity

import com.deck.common.util.*
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
    val discordRoleId: LongGenericId?,
    val discordSyncedAt: Timestamp?,
    val priority: Int,
    val botScope: RawRoleBotScope?,
    val teamId: GenericId,
    val updatedAt: Timestamp?,
    val permissions: RawRolePermissions
)

@Serializable
data class RawRolePermissions(
    val announcements: Int = 0,
    val bots: Int = 0,
    val brackets: Int = 0,
    val calendar: Int = 0,
    val chat: Int = 0,
    val customization: Int = 0,
    val docs: Int = 0,
    val forms: Int = 0,
    val forums: Int = 0,
    val general: Int = 0,
    val lists: Int = 0,
    val matchmaking: Int = 0,
    val media: Int = 0,
    val recruitment: Int = 0,
    val scheduling: Int = 0,
    val streams: Int = 0,
    val voice: Int = 0,
    val xp: Int = 0
)

/** @param channelCategoryId is missing when the channel doesn't have an ID */
@Serializable
data class RawRolePermissionsOverwritten(
    val teamId: GenericId,
    val createdAt: Timestamp,
    val updatedAt: Timestamp?,
    val teamRoleId: IntGenericId,
    val denyPermissions: RawRolePermissions,
    val allowPermissions: RawRolePermissions,
    val channelCategoryId: OptionalProperty<IntGenericId> = OptionalProperty.NotPresent
)

@Serializable
data class RawRoleBotScope(
    val userId: GenericId?
)