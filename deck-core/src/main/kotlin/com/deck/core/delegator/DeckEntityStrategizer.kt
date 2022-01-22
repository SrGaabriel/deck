package com.deck.core.delegator

import com.deck.common.content.node.NodeGlobalStrategy
import com.deck.common.entity.*
import com.deck.common.util.GenericId
import com.deck.common.util.asNullable
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.*
import com.deck.core.entity.impl.*
import com.deck.core.entity.misc.forcefullyWrap
import com.deck.core.util.BlankStatelessMember
import com.deck.core.util.BlankStatelessMessageChannel
import com.deck.core.util.BlankStatelessUser
import com.deck.rest.entity.RawFetchedTeam
import com.deck.gateway.entity.RawPartialTeamChannel
import java.util.*

public class DeckEntityStrategizer(private val client: DeckClient) : EntityStrategizer {
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
        aboutInfo = raw.aboutInfo.asNullable().forcefullyWrap(),
        creationTime = raw.joinDate.asNullable()!!,
        lastLoginTime = raw.lastOnline.asNullable()!!,
    )

    override fun decodeSelf(raw: RawUser): SelfUser = DeckSelfUser(
        client = client,
        id = raw.id,
        name = raw.name,
        subdomain = raw.subdomain.asNullable(),
        avatar = raw.profilePicture.asNullable(),
        banner = raw.profileBannerLg.asNullable(),
        aboutInfo = raw.aboutInfo.asNullable().forcefullyWrap(),
        creationTime = raw.joinDate.asNullable()!!,
        lastLoginTime = raw.lastOnline.asNullable()!!,
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

    override fun decodePartialSentMessage(channelId: UUID, teamId: GenericId?, raw: RawPartialSentMessage): Message =
        DeckMessage(
            client = client,
            id = raw.id.mapToBuiltin(),
            content = NodeGlobalStrategy.decodeContent(raw.content),
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

    override fun decodePartialTeamChannel(raw: RawPartialTeamChannel, teamId: GenericId): PartialTeamChannel =
        DeckPartialTeamChannel(
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
            userPermissions = raw.userPermissions.asNullable()?.map { it.forcefullyWrap()!! },
            roles = raw.roles.asNullable()?.map { it.forcefullyWrap(client)!! },
            rolePermissionsOverwrittenById = raw.rolesById.asNullable()?.entries?.associate {
                it.key to it.value.forcefullyWrap()!!
            } ?: emptyMap()
        )
}
