package com.guildedkt.entity

import com.guildedkt.util.GenericId
import com.guildedkt.util.GuildedUnknown
import com.guildedkt.util.IntGenericId
import com.guildedkt.util.LongGenericId
import com.guildedkt.util.Timestamp
import kotlinx.serialization.Serializable

@Serializable
@GuildedUnknown
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
