package com.deck.core.entity.impl

import com.deck.common.entity.RawChannel
import com.deck.common.entity.RawChannelCategory
import com.deck.common.util.*
import com.deck.common.util.Dictionary
import com.deck.core.DeckClient
import com.deck.core.entity.*
import com.deck.core.entity.misc.ChannelContentType
import com.deck.core.entity.misc.ChannelType
import com.deck.gateway.entity.RawPartialTeamChannel
import java.util.*

public class DeckTeamChannel(
    override val client: DeckClient,
    public val raw: RawChannel
) : TeamChannel {
    override val id: UUID get() = raw.id.mapToBuiltin()

    override val name: String get() = raw.name

    override val description: String? get() = raw.description

    override val type: ChannelType get() = raw.type

    override val contentType: ChannelContentType get() = raw.contentType

    override val createdAt: Timestamp get() = raw.createdAt

    override val createdBy: GenericId get() = raw.createdBy

    override val archivedAt: Timestamp? get() = raw.archivedAt

    override val archivedBy: GenericId? get() = raw.archivedBy

    override val updatedAt: Timestamp get() = raw.updatedAt

    override val deletedAt: Timestamp? get() = raw.deletedAt

    override val teamId: GenericId? get() = raw.teamId
}

public class DeckPartialTeamChannel(
    override val client: DeckClient,
    public val raw: RawPartialTeamChannel
) : PartialTeamChannel {
    override val id: UUID get() = raw.id.mapToBuiltin()

    override val name: String get() = raw.name

    override val description: String? get() = raw.description

    override val type: ChannelType? get() = raw.type.asNullable()

    override val contentType: ChannelContentType? get() = raw.contentType.asNullable()

    override val createdAt: Timestamp? get() = raw.createdAt.asNullable()

    override val createdBy: GenericId? get() = raw.createdBy.asNullable()

    override val archivedAt: Timestamp? get() = raw.archivedAt.asNullable()

    override val archivedBy: GenericId? get() = raw.archivedBy.asNullable()

    override val updatedAt: Timestamp? get() = raw.updatedAt.asNullable()

    override val deletedAt: Timestamp? get() = raw.deletedAt.asNullable()

    override val teamId: GenericId? get() = raw.teamId.asNullable()

    override val parentChannelId: UniqueId? get() = raw.parentChannelId.asNullable()

    override val autoArchiveAt: Timestamp? get() = raw.autoArchiveAt.asNullable()

    override val createdByWebhookId: UniqueId? get() = raw.createdByWebhookId.asNullable()

    override val archivedByWebhookId: UniqueId? get() = raw.archivedByWebhookId.asNullable()

    override val channelCategoryId: IntGenericId? get() = raw.channelCategoryId.asNullable()

    override val addedAt: Timestamp? get() = raw.addedAt.asNullable()

    override val channelId: UniqueId? get() = raw.channelId.asNullable()

    override val isRoleSynced: Boolean? get() = raw.isRoleSynced.asNullable()

    override val roles: List<DeckRole>? get() = raw.roles.asNullable()?.map { DeckRole(client, it) }

    override val userPermissions: List<UserPermission>?
        get() =
            raw.userPermissions.asNullable()?.map { DeckUserPermission(client, it) }

    override val tournamentRoles: List<Unit>? get() = raw.tournamentRoles.asNullable()

    override val isPublic: Boolean get() = raw.isPublic

    override val priority: Int? get() = raw.priority.asNullable()

    override val groupId: GenericId? get() = raw.groupId.asNullable()

    override val settings: Unit? get() = raw.settings.asNullable()

    override val groupType: String? get() = raw.groupType.asNullable()

    override val rolesById: Dictionary<String, RolePermissionsOverwritten>?
        get() =
            raw.rolesById.asNullable()?.entries?.associate {
                it.key to DeckRolePermissionsOverwritten(
                    client,
                    it.value
                )
            }

    override val tournamentsRolesById: Unit? get() = raw.tournamentsRolesById.asNullable()

    @DeckUnsupported
    override val createdByInfo: Unit?
        get() = raw.createdByInfo.asNullable()
}

public class DeckTeamChannelCategory(
    override val client: DeckClient,
    public val raw: RawChannelCategory
) : TeamChannelCategory {
    override val id: IntGenericId get() = raw.id

    override val name: String get() = raw.name

    override val priority: Int? get() = raw.priority

    override val roles: List<RolePermissionsOverwritten>?
        get() =
            raw.roles?.map { DeckRolePermissionsOverwritten(client, it) }

    override val rolesById: Dictionary<String, RolePermissionsOverwritten>
        get() =
            raw.rolesById.entries.associate { it.key to DeckRolePermissionsOverwritten(client, it.value) }

    override val teamId: GenericId get() = raw.teamId

    override val createdAt: Timestamp get() = raw.createdAt

    override val updatedAt: Timestamp? get() = raw.updatedAt

    override val groupId: GenericId get() = raw.groupId

    override val channelCategoryId: IntGenericId? get() = raw.channelCategoryId

    override val userPermissions: List<UserPermission>?
        get() =
            raw.userPermissions?.map { DeckUserPermission(client, it) }
}