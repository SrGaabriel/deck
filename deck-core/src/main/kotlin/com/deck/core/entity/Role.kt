package com.deck.core.entity

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.LongGenericId
import com.deck.core.stateless.StatelessRole
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.StatelessUser
import kotlinx.datetime.Instant

public interface Role : Entity, StatelessRole {
    public val color: String
    public val isBase: Boolean
    public val isDisplayedSeparately: Boolean
    public val isMentionable: Boolean
    public val isSelfAssignable: Boolean
    public val name: String
    public val createdAt: Instant
    public val discordRoleId: LongGenericId?
    public val discordSyncedAt: Instant?
    public val priority: Int
    public val botId: GenericId?
    public val updatedAt: Instant?
    public val permissions: RolePermissions
}

public interface RolePermissions {
    public val announcements: Int
    public val bots: Int
    public val brackets: Int
    public val calendar: Int
    public val chat: Int
    public val customization: Int
    public val docs: Int
    public val forms: Int
    public val forums: Int
    public val general: Int
    public val lists: Int
    public val matchmaking: Int
    public val media: Int
    public val recruitment: Int
    public val scheduling: Int
    public val streams: Int
    public val voice: Int
    public val xp: Int
}

public interface RolePermissionsOverride {
    public val team: StatelessTeam
    public val createdAt: Instant
    public val updatedAt: Instant?

    public val teamRoleId: IntGenericId
    public val channelCategoryId: IntGenericId?

    public val denyPermissions: RolePermissions
    public val allowPermissions: RolePermissions
}

public interface RoleBotScope {
    public val user: StatelessUser?
}