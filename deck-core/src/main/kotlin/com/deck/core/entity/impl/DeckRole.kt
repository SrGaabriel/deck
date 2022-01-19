package com.deck.core.entity.impl

import com.deck.common.entity.RawRole
import com.deck.common.entity.RawRoleBotScope
import com.deck.common.entity.RawRolePermissions
import com.deck.common.entity.RawRolePermissionsOverwritten
import com.deck.common.util.*
import com.deck.core.DeckClient
import com.deck.core.entity.Role
import com.deck.core.entity.RoleBotScope
import com.deck.core.entity.RolePermissions
import com.deck.core.entity.RolePermissionsOverwritten

public class DeckRole(
    override val client: DeckClient,
    public val raw: RawRole
) : Role {
    override val id: IntGenericId get() = raw.id

    override val color: String get() = raw.color

    override val isBase: Boolean get() = raw.isBase

    override val isDisplayedSeparately: Boolean get() = raw.isDisplayedSeparately

    override val isMentionable: Boolean get() = raw.isMentionable

    override val isSelfAssignable: Boolean get() = raw.isSelfAssignable

    override val name: String get() = raw.name

    override val createdAt: Timestamp get() = raw.createdAt

    override val discordRoleId: LongGenericId? get() = raw.discordRoleId

    override val discordSyncedAt: Timestamp? get() = raw.discordSyncedAt

    override val priority: Int get() = raw.priority

    override val botScope: RoleBotScope?
        get() = raw.botScope?.let {
            DeckRoleBotScope(
                client,
                it
            )
        }

    override val teamId: GenericId get() = raw.teamId

    override val updatedAt: Timestamp? get() = raw.updatedAt

    override val permissions: RolePermissions get() = DeckRolePermissions(raw.permissions)
}

public data class DeckRolePermissionsOverwritten(
    override val client: DeckClient,
    public val raw: RawRolePermissionsOverwritten
) : RolePermissionsOverwritten {
    override val teamId: GenericId get() = raw.teamId

    override val createdAt: Timestamp get() = raw.createdAt

    override val updatedAt: Timestamp? get() = raw.updatedAt

    override val teamRoleId: IntGenericId get() = raw.teamRoleId

    override val denyPermissions: RolePermissions get() = DeckRolePermissions(raw.denyPermissions)

    override val allowPermissions: RolePermissions get() = DeckRolePermissions(raw.allowPermissions)

    override val channelCategoryId: IntGenericId? get() = raw.channelCategoryId.asNullable()
}

public class DeckRolePermissions(
    public val raw: RawRolePermissions
) : RolePermissions {
    override val announcements: Int get() = raw.announcements

    override val bots: Int get() = raw.bots

    override val brackets: Int get() = raw.brackets

    override val chat: Int get() = raw.chat

    override val customization: Int get() = raw.customization

    override val docs: Int get() = raw.docs

    override val forms: Int get() = raw.forms

    override val forums: Int get() = raw.forums

    override val calendar: Int get() = raw.calendar

    override val general: Int get() = raw.general

    override val lists: Int get() = raw.lists

    override val matchmaking: Int get() = raw.matchmaking

    override val media: Int get() = raw.media

    override val recruitment: Int get() = raw.recruitment

    override val scheduling: Int get() = raw.scheduling

    override val streams: Int get() = raw.streams

    override val voice: Int get() = raw.voice

    override val xp: Int get() = raw.xp
}

public class DeckRoleBotScope(
    override val client: DeckClient,
    public val raw: RawRoleBotScope?
) : RoleBotScope {
    override val userId: GenericId? get() = raw?.userId
}