package com.deck.core.entity

import com.deck.common.util.*
import com.deck.common.util.Dictionary
import com.deck.core.entity.impl.DeckRole
import com.deck.core.entity.misc.ChannelContentType
import com.deck.core.entity.misc.ChannelType
import java.util.*

public interface TeamChannel : Channel {
    public val teamId: GenericId?
}

public interface PartialTeamChannel : Entity {
    public val id: UUID

    public val name: String

    public val description: String?

    public val type: ChannelType?

    public val contentType: ChannelContentType?

    public val createdAt: Timestamp?

    public val createdBy: GenericId?

    public val archivedAt: Timestamp?

    public val archivedBy: GenericId?

    public val updatedAt: Timestamp?

    public val deletedAt: Timestamp?

    public val teamId: GenericId?

    public val parentChannelId: UniqueId?

    public val autoArchiveAt: Timestamp?

    public val createdByWebhookId: UniqueId?

    public val archivedByWebhookId: UniqueId?

    public val channelCategoryId: IntGenericId?

    public val addedAt: Timestamp?

    public val channelId: UniqueId?

    public val isRoleSynced: Boolean?

    public val roles: List<DeckRole>?

    public val userPermissions: List<UserPermission>?

    @DeckUnknown
    public val tournamentRoles: List<Unit>?

    public val isPublic: Boolean

    @DeckUnknown
    public val priority: Int?

    public val groupId: GenericId?

    @DeckUnknown
    public val settings: Unit?

    @DeckUnknown
    public val groupType: String?

    public val rolesById: Dictionary<String, RolePermissionsOverwritten>?

    @DeckUnknown
    public val tournamentsRolesById: Unit?

    @DeckUnsupported
    public val createdByInfo: Unit?
}

public interface TeamChannelCategory : Entity {
    public val id: IntGenericId

    public val name: String

    public val priority: Int?

    public val roles: List<RolePermissionsOverwritten>?

    public val rolesById: Dictionary<String, RolePermissionsOverwritten>

    public val teamId: GenericId

    public val createdAt: Timestamp

    public val updatedAt: Timestamp?

    public val groupId: GenericId

    public val channelCategoryId: IntGenericId?

    public val userPermissions: List<UserPermission>?
}