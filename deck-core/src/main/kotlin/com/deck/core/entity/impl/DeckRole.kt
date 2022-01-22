package com.deck.core.entity.impl

import com.deck.common.entity.*
import com.deck.common.util.*
import com.deck.core.DeckClient
import com.deck.core.entity.Role
import com.deck.core.entity.RoleBotScope
import com.deck.core.entity.RolePermissions
import com.deck.core.entity.RolePermissionsOverwritten
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
    override val botScope: RoleBotScope?,
    override val teamId: GenericId,
    override val updatedAt: Instant?,
    override val permissions: RolePermissions
) : Role

public data class DeckRoleBotScope(
    override val userId: GenericId?
) : RoleBotScope

public data class DeckRolePermissions(
    override val announcements: Int,
    override val bots: Int,
    override val brackets: Int,
    override val calendar: Int,
    override val chat: Int,
    override val customization: Int,
    override val docs: Int,
    override val forms: Int,
    override val forums: Int,
    override val general: Int,
    override val lists: Int,
    override val matchmaking: Int,
    override val media: Int,
    override val recruitment: Int,
    override val scheduling: Int,
    override val streams: Int,
    override val voice: Int,
    override val xp: Int
) : RolePermissions

public data class DeckRolePermissionsOverwritten(
    override val teamId: GenericId,
    override val createdAt: Instant,
    override val updatedAt: Instant?,
    override val teamRoleId: IntGenericId,
    override val channelCategoryId: IntGenericId?,
    override val denyPermissions: RolePermissions,
    override val allowPermissions: RolePermissions
) : RolePermissionsOverwritten

internal fun RawRole?.forcefullyWrap(client: DeckClient): DeckRole? {
    val raw = this ?: return null

    return DeckRole(
        client = client,
        id = raw.id,
        color = raw.color,
        isBase = raw.isBase,
        isDisplayedSeparately = raw.isDisplayedSeparately,
        isMentionable = raw.isMentionable,
        isSelfAssignable = raw.isSelfAssignable,
        name = raw.name,
        createdAt = raw.createdAt,
        discordRoleId = raw.discordRoleId,
        discordSyncedAt = raw.discordSyncedAt,
        priority = raw.priority,
        botScope = raw.botScope.forcefullyWrap(),
        teamId = raw.teamId,
        updatedAt = raw.updatedAt,
        permissions = raw.permissions.forcefullyWrap()!!
    )
}

internal fun RawRoleBotScope?.forcefullyWrap(): DeckRoleBotScope? {
    val raw = this ?: return null

    return DeckRoleBotScope(
        userId = raw.userId
    )
}

internal fun RawRolePermissions?.forcefullyWrap(): DeckRolePermissions? {
    val raw = this ?: return null

    return DeckRolePermissions(
        announcements = raw.announcements,
        bots = raw.bots,
        brackets = raw.brackets,
        calendar = raw.calendar,
        chat = raw.chat,
        customization = raw.customization,
        docs = raw.docs,
        forms = raw.forms,
        forums = raw.forums,
        general = raw.general,
        lists = raw.lists,
        matchmaking = raw.matchmaking,
        media = raw.media,
        recruitment = raw.recruitment,
        scheduling = raw.scheduling,
        streams = raw.streams,
        voice = raw.voice,
        xp = raw.xp
    )
}

internal fun RawRolePermissionsOverwritten?.forcefullyWrap() : DeckRolePermissionsOverwritten? {
    val raw = this ?: return null

    return DeckRolePermissionsOverwritten(
        teamId = raw.teamId,
        createdAt = raw.createdAt,
        updatedAt = raw.updatedAt,
        teamRoleId = raw.teamRoleId,
        channelCategoryId = raw.channelCategoryId.asNullable(),
        denyPermissions = raw.denyPermissions.forcefullyWrap()!!,
        allowPermissions = raw.allowPermissions.forcefullyWrap()!!
    )
}