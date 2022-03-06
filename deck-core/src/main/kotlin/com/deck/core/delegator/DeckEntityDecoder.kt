package com.deck.core.delegator

import com.deck.common.content.node.decode
import com.deck.common.entity.*
import com.deck.common.util.GenericId
import com.deck.common.util.asNullable
import com.deck.common.util.getValue
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.*
import com.deck.core.entity.channel.*
import com.deck.core.entity.impl.*
import com.deck.core.entity.impl.channel.*
import com.deck.gateway.entity.RawPartialTeamChannel
import com.deck.rest.entity.RawChannelForumThreadReply
import com.deck.rest.entity.RawFetchedMember
import com.deck.rest.entity.RawFetchedTeam
import java.util.*

public class DeckEntityDecoder(private val client: DeckClient) : EntityDecoder {
    override fun decodeTeam(raw: RawFetchedTeam): Team = DeckTeam(
        client = client,
        id = raw.id,
        name = raw.name,
        description = raw.description,
        ownerId = raw.ownerId,
        baseGroup = decodeGroup(raw.baseGroup),
        memberIds = raw.members.map { it.id },
        createdAt = raw.createdAt,
    )

    override fun decodeUser(raw: RawUser): User = DeckUser(
        client = client,
        id = raw.id,
        name = raw.name,
        subdomain = raw.subdomain.asNullable(),
        avatar = raw.profilePicture.asNullable(),
        banner = raw.profileBannerSm.asNullable(),
        biography = raw.aboutInfo.asNullable()?.bio?.asNullable(),
        tagline = raw.aboutInfo.asNullable()?.tagLine?.asNullable(),
        creationTime = raw.joinDate.getValue(),
        lastLoginTime = raw.lastOnline.getValue(),
    )

    override fun decodeSelf(raw: RawSelfUser): SelfUser = DeckSelfUser(
        client = client,
        id = raw.user.id,
        name = raw.user.name,
        subdomain = raw.user.subdomain.asNullable(),
        avatar = raw.user.profilePicture.asNullable(),
        banner = raw.user.profileBannerLg.asNullable(),
        biography = raw.user.aboutInfo.asNullable()?.bio?.asNullable(),
        tagline = raw.user.aboutInfo.asNullable()?.tagLine?.asNullable(),
        creationTime = raw.user.joinDate.getValue(),
        lastLoginTime = raw.user.lastOnline.getValue(),
        teamIds = raw.teams.map { it.id }
    )

    override fun decodeChannel(raw: RawChannel): Channel = raw.run {
        if (raw.teamId == null)
            return DeckPrivateChannel(
                client = client,
                id = id.mapToBuiltin(),
                name = name,
                description = description.orEmpty(),
                type = type,
                contentType = contentType,
                createdAt = createdAt,
                archivedAt = archivedAt,
                creatorId = raw.createdBy,
                archiverId = raw.archivedBy,
                updatedAt = updatedAt,
                deletedAt = deletedAt,
            )
        return when (raw.contentType) {
            RawChannelContentType.Chat -> DeckTeamMessageChannel(
                client = client,
                id = id.mapToBuiltin(),
                name = name,
                description = description.orEmpty(),
                type = type,
                contentType = contentType,
                createdAt = createdAt,
                archivedAt = archivedAt,
                creatorId = raw.createdBy,
                archiverId = raw.archivedBy,
                updatedAt = updatedAt,
                deletedAt = deletedAt,
                teamId = teamId!!
            )
            RawChannelContentType.Forum -> DeckForumChannel(
                client = client,
                id = id.mapToBuiltin(),
                name = name,
                description = description.orEmpty(),
                type = type,
                contentType = contentType,
                createdAt = createdAt,
                archivedAt = archivedAt,
                creatorId = raw.createdBy,
                archiverId = raw.archivedBy,
                updatedAt = updatedAt,
                deletedAt = deletedAt,
                teamId = teamId!!
            )
            RawChannelContentType.Scheduling -> DeckSchedulingChannel(
                client = client,
                id = id.mapToBuiltin(),
                name = name,
                description = description.orEmpty(),
                type = type,
                contentType = contentType,
                createdAt = createdAt,
                archivedAt = archivedAt,
                creatorId = raw.createdBy,
                archiverId = raw.archivedBy,
                updatedAt = updatedAt,
                deletedAt = deletedAt,
                teamId = teamId!!
            )
            else -> DeckTeamChannel(
                client = client,
                id = id.mapToBuiltin(),
                name = name,
                description = description.orEmpty(),
                type = type,
                contentType = contentType,
                createdAt = createdAt,
                archivedAt = archivedAt,
                creatorId = raw.createdBy,
                archiverId = raw.archivedBy,
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
        priority = raw.priority,
        teamId = raw.teamId,
        updatedAt = raw.updatedAt,
        permissions = decodeRolePermissions(raw.permissions)
    )

    override fun decodeGroup(raw: RawGroup): Group = DeckGroup(
        client = client,
        id = raw.id,
        teamId = raw.teamId
    )

    override fun decodeMember(teamId: GenericId, raw: RawFetchedMember): Member = DeckMember(
        client = client,
        id = raw.id,
        name = raw.name,
        nickname = raw.nickname.asNullable(),
        avatar = raw.profilePicture.asNullable(),
        userId = raw.id,
        teamId = teamId
    )

    override fun decodeRolePermissions(raw: RawRolePermissions): RolePermissions = RolePermissions(
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

    override fun decodeRolePermissionsOverride(raw: RawRolePermissionsOverride): RolePermissionsOverride =
        DeckRolePermissionsOverride(
            teamId = raw.teamId,
            createdAt = raw.createdAt,
            updatedAt = raw.updatedAt,
            teamRoleId = raw.teamRoleId,
            channelCategoryId = raw.channelCategoryId.asNullable(),
            denyPermissions = decodeRolePermissions(raw.denyPermissions),
            allowPermissions = decodeRolePermissions(raw.allowPermissions)
        )

    override fun decodeUserPermissionsOverride(teamId: GenericId, raw: RawUserPermission): UserPermissionsOverride =
        DeckUserPermissionsOverride(
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
            channelId = channelId,
            createdAt = raw.createdAt,
            authorId = raw.createdBy,
            updatedAt = null,
            editorId = null,
            isPrivate = raw.isPrivate,
            teamId = teamId,
            repliesToId = raw.repliesToIds.firstOrNull()?.mapToBuiltin()
        )

    override fun decodePartialTeamChannel(teamId: GenericId, raw: RawPartialTeamChannel): PartialTeamChannel =
        DeckPartialTeamChannel(
            client = client,
            id = raw.id.mapToBuiltin(),
            name = raw.name,
            description = raw.description.orEmpty(),
            type = RawChannelType.Team,
            contentType = raw.contentType.getValue(),
            createdAt = raw.createdAt.getValue(),
            creatorId = raw.createdBy.getValue(),
            archivedAt = raw.archivedAt.asNullable(),
            archiverId = raw.archivedBy.asNullable(),
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
            userPermissionOverrides = raw.userPermissions.asNullable()
                ?.map { decodeUserPermissionsOverride(teamId, it) },
            roles = raw.roles.asNullable()?.map { decodeRole(it) },
            rolePermissionsOverrideById = raw.rolesById.asNullable()
                ?.mapValues { decodeRolePermissionsOverride(it.value) } ?: emptyMap()
        )

    override fun decodeForumThread(raw: RawChannelForumThread): ForumThread {
        val channelId = raw.channelId.mapToBuiltin()
        return DeckForumThread(
            client = client,
            id = raw.id,
            title = raw.title,
            originalPost = DeckForumPost(
                client = client,
                id = raw.id,
                content = raw.message.decode(),
                threadId = raw.id,
                teamId = raw.teamId,
                channelId = channelId,
                authorId = raw.createdBy,
                createdAt = raw.createdAt
            ),
            channelId = channelId,
            teamId = raw.teamId,
            authorId = raw.createdBy,
            editedAt = raw.editedAt,
            isSticky = raw.isSticky,
            isShare = raw.isShare,
            isLocked = raw.isLocked,
            isDeleted = raw.isDeleted,
            createdAt = raw.createdAt
        )
    }

    override fun decodeForumThreadReply(
        channelId: UUID,
        teamId: GenericId,
        raw: RawChannelForumThreadReply
    ): ForumPost = DeckForumPost(
        client = client,
        id = raw.id,
        content = raw.content.decode(),
        threadId = raw.repliesTo,
        teamId = teamId,
        channelId = channelId,
        authorId = raw.createdBy,
        createdAt = raw.createdAt
    )

    override fun decodeScheduleAvailability(raw: RawChannelAvailability): ScheduleAvailability =
        DeckScheduleAvailability(
            client = client,
            id = raw.id,
            teamId = raw.teamId,
            channelId = raw.channelId.mapToBuiltin()
        )
}