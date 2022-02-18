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
import com.deck.core.entity.misc.DeckUserAboutInfo
import com.deck.core.stateless.StatelessTeam
import com.deck.core.util.*
import com.deck.gateway.entity.RawPartialTeamChannel
import com.deck.rest.entity.RawChannelForumThreadReply
import com.deck.rest.entity.RawFetchedMember
import com.deck.rest.entity.RawFetchedTeam
import java.util.*

public class DeckEntityDecoder(private val client: DeckClient) : EntityDecoder {
    override fun decodeTeam(raw: RawFetchedTeam): Team {
        val puppetStatelessTeam: StatelessTeam = BlankStatelessTeam(client, raw.id)
        return DeckTeam(
            client = client,
            id = raw.id,
            name = raw.name,
            description = raw.description,
            owner = BlankStatelessUser(client, raw.ownerId),
            baseGroup = decodeGroup(raw.baseGroup),
            members = raw.members.map { BlankStatelessMember(client, it.id, puppetStatelessTeam) },
            createdAt = raw.createdAt,
            discordGuildId = raw.discordGuildId,
            discordGuildName = raw.discordServerName
        )
    }

    override fun decodeUser(raw: RawUser): User = DeckUser(
        client = client,
        id = raw.id,
        name = raw.name,
        subdomain = raw.subdomain.asNullable(),
        avatar = raw.profilePicture.asNullable(),
        banner = raw.profileBannerSm.asNullable(),
        aboutInfo = DeckUserAboutInfo.from(raw.aboutInfo.asNullable()),
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
        aboutInfo = DeckUserAboutInfo.from(raw.user.aboutInfo.asNullable()),
        creationTime = raw.user.joinDate.getValue(),
        lastLoginTime = raw.user.lastOnline.getValue(),
        teams = raw.teams.map { BlankStatelessTeam(client, it.id) }
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
                createdBy = BlankStatelessUser(client, createdBy),
                archivedAt = archivedAt,
                archivedBy = archivedBy?.let { BlankStatelessUser(client, it) },
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
                createdBy = BlankStatelessUser(client, createdBy),
                archivedAt = archivedAt,
                archivedBy = archivedBy?.let { BlankStatelessUser(client, it) },
                updatedAt = updatedAt,
                deletedAt = deletedAt,
                team = BlankStatelessTeam(client, teamId!!)
            )
            RawChannelContentType.Forum -> DeckForumChannel(
                client = client,
                id = id.mapToBuiltin(),
                name = name,
                description = description.orEmpty(),
                type = type,
                contentType = contentType,
                createdAt = createdAt,
                createdBy = BlankStatelessUser(client, createdBy),
                archivedAt = archivedAt,
                archivedBy = archivedBy?.let { BlankStatelessUser(client, it) },
                updatedAt = updatedAt,
                deletedAt = deletedAt,
                team = BlankStatelessTeam(client, teamId!!)
            )
            RawChannelContentType.Scheduling -> DeckSchedulingChannel(
                client = client,
                id = id.mapToBuiltin(),
                name = name,
                description = description.orEmpty(),
                type = type,
                contentType = contentType,
                createdAt = createdAt,
                createdBy = BlankStatelessUser(client, createdBy),
                archivedAt = archivedAt,
                archivedBy = archivedBy?.let { BlankStatelessUser(client, it) },
                updatedAt = updatedAt,
                deletedAt = deletedAt,
                team = BlankStatelessTeam(client, teamId!!)
            )
            else -> DeckTeamChannel(
                client = client,
                id = id.mapToBuiltin(),
                name = name,
                description = description.orEmpty(),
                type = type,
                contentType = contentType,
                createdAt = createdAt,
                createdBy = BlankStatelessUser(client, createdBy),
                archivedAt = archivedAt,
                archivedBy = archivedBy?.let { BlankStatelessUser(client, it) },
                updatedAt = updatedAt,
                deletedAt = deletedAt,
                team = BlankStatelessTeam(client, teamId!!)
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
        team = BlankStatelessTeam(client, raw.teamId),
        updatedAt = raw.updatedAt,
        permissions = decodeRolePermissions(raw.permissions)
    )

    override fun decodeGroup(raw: RawGroup): Group = DeckGroup(
        client = client,
        id = raw.id,
        team = BlankStatelessTeam(client, raw.teamId)
    )

    override fun decodeMember(teamId: GenericId, raw: RawFetchedMember): Member = DeckMember(
        client = client,
        id = raw.id,
        name = raw.name,
        nickname = raw.nickname.asNullable(),
        avatar = raw.profilePicture.asNullable(),
        user = BlankStatelessUser(client, raw.id),
        team = BlankStatelessTeam(client, teamId)
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
        team = BlankStatelessTeam(client, raw.teamId),
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

    override fun decodePartialSentMessage(channelId: UUID, teamId: GenericId?, raw: RawPartialSentMessage): Message {
        val team: StatelessTeam? = teamId?.let { id -> BlankStatelessTeam(client, id) }
        return DeckMessage(
            client = client,
            id = raw.id.mapToBuiltin(),
            content = raw.content.decode(),
            channel = BlankStatelessMessageChannel(client, channelId, team),
            createdAt = raw.createdAt,
            author = BlankStatelessUser(client, raw.createdBy),
            updatedAt = null,
            editor = null,
            isPrivate = raw.isPrivate,
            team = team,
            repliesToId = raw.repliesToIds.firstOrNull()?.mapToBuiltin()
        )
    }

    override fun decodePartialTeamChannel(teamId: GenericId, raw: RawPartialTeamChannel): PartialTeamChannel = DeckPartialTeamChannel(
        client = client,
        id = raw.id.mapToBuiltin(),
        name = raw.name,
        description = raw.description.orEmpty(),
        type = RawChannelType.Team,
        contentType = raw.contentType.getValue(),
        createdAt = raw.createdAt.getValue(),
        createdBy = BlankStatelessUser(client, raw.createdBy.getValue()),
        archivedAt = raw.archivedAt.asNullable(),
        archivedBy = raw.archivedBy.asNullable()?.let { archievedBy -> BlankStatelessUser(client, archievedBy) } ,
        updatedAt = raw.updatedAt.asNullable(),
        deletedAt = raw.deletedAt.asNullable(),
        team = BlankStatelessTeam(client, teamId),
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

    override fun decodeForumThread(raw: RawChannelForumThread): ForumThread {
        val team = BlankStatelessTeam(client, raw.teamId)
        val author = BlankStatelessUser(client, raw.createdBy)
        val channel = BlankStatelessForumChannel(client, raw.channelId.mapToBuiltin(), team)
        val statelessThread = BlankStatelessForumThread(client, raw.id, team, channel)

        return DeckForumThread(
            client = client,
            id = raw.id,
            title = raw.title,
            originalPost = DeckForumPost(
                client = client,
                id = raw.id,
                content = raw.message.decode(),
                thread = statelessThread,
                team = team,
                channel = channel,
                author = author,
                createdAt = raw.createdAt
            ),
            channel = channel,
            team = team,
            author = author,
            editedAt = raw.editedAt,
            isSticky = raw.isSticky,
            isShare = raw.isShare,
            isLocked = raw.isLocked,
            isDeleted = raw.isDeleted,
            createdAt = raw.createdAt
        )
    }

    override fun decodeForumThreadReply(channelId: UUID, raw: RawChannelForumThreadReply): ForumPost {
        val team = InvalidStatelessTeam(client)
        val channel = BlankStatelessForumChannel(client, channelId, team)
        return DeckForumPost(
            client = client,
            id = raw.id,
            content = raw.content.decode(),
            thread = BlankStatelessForumThread(client, raw.repliesTo, team, channel),
            team = team,
            channel = channel,
            author = BlankStatelessUser(client, raw.createdBy),
            createdAt = raw.createdAt
        )
    }

    override fun decodeScheduleAvailability(raw: RawChannelAvailability): ScheduleAvailability {
        val team = BlankStatelessTeam(client, raw.teamId)
        return DeckScheduleAvailability(
            client = client,
            id = raw.id,
            team = team,
            channel = BlankStatelessSchedulingChannel(client, raw.channelId.mapToBuiltin(), team)
        )
    }
}