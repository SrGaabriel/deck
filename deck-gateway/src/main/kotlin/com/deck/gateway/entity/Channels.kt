package com.deck.gateway.com.deck.gateway.entity

import com.deck.common.entity.*
import com.deck.common.util.*
import com.deck.gateway.entity.RawUserIdObject
import kotlinx.serialization.Serializable

// All optional fields are missing on channel update (except on role updates)
@Serializable
data class RawPartialTeamChannel(
    val id: UniqueId,
    val type: OptionalProperty<RawChannelType> = OptionalProperty.NotPresent,
    val createdAt: OptionalProperty<Timestamp> = OptionalProperty.NotPresent,
    val createdBy: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    val updatedAt: OptionalProperty<Timestamp?> = OptionalProperty.NotPresent,
    val name: String,
    val contentType: OptionalProperty<RawChannelContentType> = OptionalProperty.NotPresent,
    val archivedAt: OptionalProperty<Timestamp?> = OptionalProperty.NotPresent,
    val parentChannelId: OptionalProperty<UniqueId?> = OptionalProperty.NotPresent,
    val autoArchiveAt: OptionalProperty<Timestamp?> = OptionalProperty.NotPresent,
    val deletedAt: OptionalProperty<Timestamp?> = OptionalProperty.NotPresent,
    val archivedBy: OptionalProperty<GenericId?> = OptionalProperty.NotPresent,
    val description: String?,
    val createdByWebhookId: OptionalProperty<UniqueId?> = OptionalProperty.NotPresent,
    val archivedByWebhookId: OptionalProperty<UniqueId?> = OptionalProperty.NotPresent,
    val teamId: OptionalProperty<GenericId?> = OptionalProperty.NotPresent,
    val channelCategoryId: OptionalProperty<IntGenericId?> = OptionalProperty.NotPresent,
    val addedAt: OptionalProperty<Timestamp?> = OptionalProperty.NotPresent,
    val channelId: OptionalProperty<UniqueId?> = OptionalProperty.NotPresent,
    val isRoleSynced: OptionalProperty<Boolean?> = OptionalProperty.NotPresent,
    val roles: OptionalProperty<List<RawRole>?> = OptionalProperty.NotPresent,
    val userPermissions: OptionalProperty<List<RawUserPermission>?> = OptionalProperty.NotPresent,
    @DeckUnknown val tournamentRoles: OptionalProperty<List<Unit>?> = OptionalProperty.NotPresent,
    val isPublic: Boolean,
    @DeckUnknown val priority: OptionalProperty<Int?> = OptionalProperty.NotPresent,
    val groupId: OptionalProperty<GenericId?> = OptionalProperty.NotPresent,
    @DeckUnknown val settings: OptionalProperty<Unit?> = OptionalProperty.NotPresent,
    @DeckUnknown val groupType: OptionalProperty<String> = OptionalProperty.NotPresent,
    val rolesById: OptionalProperty<Dictionary<String, RawRolePermissionsOverwritten>> = OptionalProperty.NotPresent,
    @DeckUnknown val tournamentsRolesById: OptionalProperty<Unit?> = OptionalProperty.NotPresent,
    @DeckUnsupported val createdByInfo: OptionalProperty<Unit?> = OptionalProperty.NotPresent
)

@Serializable
data class RawPartialPrivateChannel(
    val id: UniqueId,
    val name: OptionalProperty<String> = OptionalProperty.NotPresent,
    val voiceParticipants: OptionalProperty<List<RawUserIdObject>> = OptionalProperty.NotPresent
)

// Received when deleting categories
@Serializable
data class RawTeamCategoryChannel(
    val id: UniqueId,
    val channelCategoryId: IntGenericId?,
    val isRoleSynced: OptionalProperty<Boolean> = OptionalProperty.NotPresent,
    val rolesById: OptionalProperty<Dictionary<String, RawRolePermissionsOverwritten>> = OptionalProperty.NotPresent,
    val userPermissions: OptionalProperty<List<RawUserPermission>> = OptionalProperty.NotPresent
)

// Received when moving categories between groups
@Serializable
data class RawTeamCategoryChannelId(val id: UniqueId)

@Serializable
data class RawPrivateChannelRemovedInfo(val user: RawUserIdObject)