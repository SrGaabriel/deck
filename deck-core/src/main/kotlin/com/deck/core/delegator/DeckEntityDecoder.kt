package com.deck.core.delegator

import com.deck.common.content.node.decode
import com.deck.common.entity.*
import com.deck.common.util.GenericId
import com.deck.common.util.asNullable
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.*
import com.deck.core.entity.channel.Channel
import com.deck.core.entity.channel.PartialTeamChannel
import com.deck.core.entity.impl.*
import com.deck.core.entity.misc.DeckUserAboutInfo
import com.deck.core.util.BlankStatelessMember
import com.deck.core.util.BlankStatelessMessageChannel
import com.deck.core.util.BlankStatelessTeam
import com.deck.core.util.BlankStatelessUser
import com.deck.gateway.entity.RawPartialTeamChannel
import com.deck.rest.entity.RawFetchedTeam
import com.deck.rest.request.SelfUserResponse
import java.util.*

public class DeckEntityDecoder(private val client: DeckClient) : EntityDecoder {
    override fun decodeTeam(raw: RawFetchedTeam): Team = DeckTeam(
        client = client,
        id = raw.id,
        name = raw.name,
        description = raw.description,
        owner = BlankStatelessUser(client, raw.ownerId),
        members = raw.members.map { BlankStatelessMember(client, it.id) },
        createdAt = raw.createdAt,
        discordGuildId = raw.discordGuildId,
        discordGuildName = raw.discordServerName
    )

    override fun decodeUser(raw: RawUser): User = DeckUser(
        client = client,
        id = raw.id,
        name = raw.name,
        subdomain = raw.subdomain.asNullable(),
        avatar = raw.profilePicture.asNullable(),
        banner = raw.profileBannerSm.asNullable(),
        aboutInfo = raw.aboutInfo.asNullable()?.let { DeckUserAboutInfo.from(it) },
        creationTime = raw.joinDate.asNullable()!!,
        lastLoginTime = raw.lastOnline.asNullable()!!,
    )

    override fun decodeSelf(raw: SelfUserResponse): SelfUser = DeckSelfUser(
        client = client,
        id = raw.user.id,
        name = raw.user.name,
        subdomain = raw.user.subdomain.asNullable(),
        avatar = raw.user.profilePicture.asNullable(),
        banner = raw.user.profileBannerLg.asNullable(),
        aboutInfo = raw.user.aboutInfo.asNullable()?.let { DeckUserAboutInfo.from(it) },
        creationTime = raw.user.joinDate.asNullable()!!,
        lastLoginTime = raw.user.lastOnline.asNullable()!!,
        teams = raw.teams.map { BlankStatelessTeam(client, it.id) }
    )

    override fun decodeChannel(raw: RawChannel): Channel = raw.run {
        when (teamId) {
            null -> DeckPrivateChannel(
                client = client,
                id = id.mapToBuiltin(),
                name = name,
                description = description.orEmpty(),
                type = type,
                contentType = contentType,
                createdAt = createdAt,
                createdBy = createdBy,
                archivedAt = archivedAt,
                archivedBy = archivedBy,
                updatedAt = updatedAt,
                deletedAt = deletedAt
            )
            else -> DeckTeamChannel(
                client = client,
                id = id.mapToBuiltin(),
                name = name,
                description = description.orEmpty(),
                type = type,
                contentType = contentType,
                createdAt = createdAt,
                createdBy = createdBy,
                archivedAt = archivedAt,
                archivedBy = archivedBy,
                updatedAt = updatedAt,
                deletedAt = deletedAt,
                teamId = teamId!!
            )
        }
    }

    override fun decodeRole(raw: RawRole): Role = DeckRole(
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
        botId = raw.botScope?.userId,
        teamId = raw.teamId,
        updatedAt = raw.updatedAt,
        permissions = decodeRolePermissions(raw.permissions)
    )

    override fun decodeRolePermissions(raw: RawRolePermissions): RolePermissions = DeckRolePermissions(
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
        xp = raw.xp,
    )

    override fun decodeRolePermissionsOverride(raw: RawRolePermissionsOverride): RolePermissionsOverride = DeckRolePermissionsOverride(
        teamId = raw.teamId,
        createdAt = raw.createdAt,
        updatedAt = raw.updatedAt,
        teamRoleId = raw.teamRoleId,
        channelCategoryId = raw.channelCategoryId.asNullable(),
        denyPermissions = decodeRolePermissions(raw.denyPermissions),
        allowPermissions = decodeRolePermissions(raw.allowPermissions)
    )

    override fun decodeUserPermissionsOverride(teamId: GenericId, raw: RawUserPermission): UserPermissionsOverride = DeckUserPermissionsOverride(
        user = BlankStatelessUser(client, raw.userId),
        channelId = raw.channelId.asNullable()?.mapToBuiltin(),
        createdAt = raw.createdAt,
        updatedAt = raw.updatedAt,
        denyPermissions = decodeRolePermissions(raw.denyPermissions),
        allowPermissions = decodeRolePermissions(raw.allowPermissions)
    )

    override fun decodePartialSentMessage(channelId: UUID, teamId: GenericId?, raw: RawPartialSentMessage): Message =
        DeckMessage(
            client = client,
            id = raw.id.mapToBuiltin(),
            content = raw.content.decode(),
            channel = BlankStatelessMessageChannel(client, channelId, teamId),
            createdAt = raw.createdAt,
            createdBy = raw.createdBy,
            updatedAt = null,
            updatedBy = null,
            isSilent = raw.isSilent,
            isPrivate = raw.isPrivate,
            teamId = teamId,
            repliesToId = raw.repliesToIds.firstOrNull()?.mapToBuiltin()
        )

    override fun decodePartialTeamChannel(teamId: GenericId, raw: RawPartialTeamChannel): PartialTeamChannel = DeckPartialTeamChannel(
        client = client,
        id = raw.id.mapToBuiltin(),
        name = raw.name,
        description = raw.description.orEmpty(),
        type = RawChannelType.Team,
        contentType = raw.contentType.asNullable()!!,
        createdAt = raw.createdAt.asNullable()!!,
        createdBy = raw.createdBy.asNullable()!!,
        archivedAt = raw.archivedAt.asNullable(),
        archivedBy = raw.archivedBy.asNullable(),
        updatedAt = raw.updatedAt.asNullable(),
        deletedAt = raw.deletedAt.asNullable(),
        teamId = teamId,
        parentChannelId = raw.parentChannelId.asNullable()?.mapToBuiltin(),
        channelCategoryId = raw.channelCategoryId.asNullable(),
        channelId = raw.channelId.asNullable()?.mapToBuiltin(),
        groupId = raw.groupId.asNullable(),
        createdByWebhookId = raw.createdByWebhookId.asNullable()?.mapToBuiltin(),
        archivedByWebhookId = raw.archivedByWebhookId.asNullable()?.mapToBuiltin(),
        addedAt = raw.addedAt.asNullable(),
        autoArchiveAt = raw.autoArchiveAt.asNullable(),
        isPublic = raw.isPublic,
        isRoleSynced = raw.isRoleSynced.asNullable(),
        userPermissionOverrides = raw.userPermissions.asNullable()?.map { decodeUserPermissionsOverride(teamId, it) },
        roles = raw.roles.asNullable()?.map { decodeRole(it) },
        rolePermissionsOverrideById = raw.rolesById.asNullable()?.mapValues { decodeRolePermissionsOverride(it.value) } ?: emptyMap()
    )
}