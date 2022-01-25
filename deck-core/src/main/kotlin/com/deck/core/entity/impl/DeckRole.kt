package com.deck.core.entity.impl

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.LongGenericId
import com.deck.core.DeckClient
import com.deck.core.entity.Role
import com.deck.core.entity.RolePermissions
import com.deck.core.entity.RolePermissionsOverride
import kotlinx.datetime.Instant

public data class DeckRole(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val color: String,
    override val isBase: Boolean,
    override val isDisplayedSeparately: Boolean,
    override val isMentionable: Boolean,
    override val isSelfAssignable: Boolean,
    override val name: String,
    override val createdAt: Instant,
    override val discordRoleId: LongGenericId?,
    override val discordSyncedAt: Instant?,
    override val priority: Int,
    override val botId: GenericId?,
    override val teamId: GenericId,
    override val updatedAt: Instant?,
    override val permissions: RolePermissions
) : Role

public data class DeckRolePermissions(
    override val announcements: Int = 0,
    override val bots: Int = 0,
    override val brackets: Int = 0,
    override val calendar: Int = 0,
    override val chat: Int = 0,
    override val customization: Int = 0,
    override val docs: Int = 0,
    override val forms: Int = 0,
    override val forums: Int = 0,
    override val general: Int = 0,
    override val lists: Int = 0,
    override val matchmaking: Int = 0,
    override val media: Int = 0,
    override val recruitment: Int = 0,
    override val scheduling: Int = 0,
    override val streams: Int = 0,
    override val voice: Int = 0,
    override val xp: Int = 0
) : RolePermissions

public data class DeckRolePermissionsOverride(
    override val teamId: GenericId,
    override val createdAt: Instant,
    override val updatedAt: Instant?,
    override val teamRoleId: IntGenericId,
    override val channelCategoryId: IntGenericId?,
    override val denyPermissions: RolePermissions,
    override val allowPermissions: RolePermissions
) : RolePermissionsOverride