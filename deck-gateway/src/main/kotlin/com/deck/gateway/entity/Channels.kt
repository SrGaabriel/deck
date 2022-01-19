package com.deck.gateway.entity

import com.deck.common.entity.*
import com.deck.common.util.*
import kotlinx.serialization.Serializable

// All optional fields are missing on channel update (except on role updates)
@Serializable
public data class RawPartialTeamChannel(
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
public data class RawPartialPrivateChannel(
    val id: UniqueId,
    val name: OptionalProperty<String> = OptionalProperty.NotPresent,
    val voiceParticipants: OptionalProperty<List<RawUserIdObject>> = OptionalProperty.NotPresent
)

// Received when deleting categories
@Serializable
public data class RawTeamCategoryChannel(
    val id: UniqueId,
    val channelCategoryId: IntGenericId?,
    val isRoleSynced: OptionalProperty<Boolean> = OptionalProperty.NotPresent,
    val rolesById: OptionalProperty<Dictionary<String, RawRolePermissionsOverwritten>> = OptionalProperty.NotPresent,
    val userPermissions: OptionalProperty<List<RawUserPermission>> = OptionalProperty.NotPresent
)

// Received when moving categories between groups
@Serializable
public data class RawTeamCategoryChannelId(val id: UniqueId)

@Serializable
public data class RawPrivateChannelRemovedInfo(val user: RawUserIdObject)

@Serializable
public data class RawChannelEvent(
    val id: IntGenericId,
    val name: String,
    val description: RawMessageContent?,
    val repeats: Boolean,
    val link: String?,
    val location: String?,
    val visibility: String,
    val allowedRoleIds: OptionalProperty<List<IntGenericId>> = OptionalProperty.NotPresent,
    val createdAt: Timestamp,
    val teamId: GenericId,
    val createdBy: GenericId,
    val gameId: Int?,
    val happensAt: Timestamp,
    val notifiedAt: Timestamp?,
    val familyId: UniqueId,
    // val extraInfo: *?,
    // val repeatsEvery: *,
    val isSynthetic: Boolean,
    val durationInMinutes: Long,
    val isPublic: Boolean,
    val colorLabel: String,
    // val happensAtClientOffset: *?,
    val isAllDay: Boolean,
    val happensAtClientTimezone: String,
    val isOpen: Boolean,
    val rsvpLimit: Int?,
    val discordChannelId: Long?,
    val recurringNotifiedAt: Timestamp?,
    val channelId: UniqueId,
    val isPrivate: Boolean,
    val isCancelled: Boolean,
    val eventId: IntGenericId,
    val autofillWaitlist: Boolean,
    val customFormTemplateId: IntGenericId?,
    val customFormId: IntGenericId?,
    val rsvpNotificationsEnabled: Boolean,
    val eventCountdownEnabled: Boolean,
    val eventRecurringReminderEnabled: Boolean,
    val roleMentioningEnabled: Boolean,
    val mentionEveryone: Boolean
)

/** @param assignedTo is not present when editing items */
@Serializable
public data class RawChannelListItem(
    val id: UniqueId,
    val message: RawMessageContent,
    val priority: Int,
    val note: RawMessageContent?,
    val channelId: UniqueId,
    val createdAt: Timestamp,
    val createdBy: GenericId,
    val updatedBy: GenericId?,
    val completedBy: GenericId?,
    val completedAt: Timestamp?,
    val deletedBy: GenericId?,
    val deletedAt: Timestamp?,
    val parentId: IntGenericId?,
    val noteCreatedAt: Timestamp?,
    val noteCreatedBy: GenericId?,
    val noteUpdatedAt: Timestamp?,
    val noteUpdatedBy: GenericId?,
    val teamId: GenericId,
    val webhookId: UniqueId?,
    val clonedFromId: UniqueId?,
    val assignedTo: OptionalProperty<List<GenericId>> = OptionalProperty.NotPresent
)

@Serializable
public data class RawChannelForumThread(
    val id: IntGenericId,
    val title: String,
    val message: RawMessageContent,
    val visibility: String,
    val createdAt: Timestamp,
    val bumpedAt: Timestamp,
    val editedAt: Timestamp?,
    val createdBy: GenericId,
    val teamId: GenericId,
    val gameId: Int?,
    val isSticky: Boolean,
    val isShare: Boolean,
    val isDeleted: Boolean,
    val categoryId: IntGenericId?,
    val isLocked: Boolean,
    val channelId: UniqueId,
    val replyCount: Int
)

@Serializable
public data class RawPartialChannelForumThread(
    val id: IntGenericId,
    val title: String,
    val message: RawMessageContent,
    val editedAt: Timestamp
)

@Serializable
public data class RawChannelScheduleAvailability(
    val id: IntGenericId,
    val availabilityId: IntGenericId,
    val teamId: GenericId,
    val channelId: UniqueId,
    val userId: GenericId,
    val createdAt: Timestamp,
    val updatedAt: Timestamp,
    val startDate: Timestamp,
    val endDate: Timestamp
)

@Serializable
public data class RawChannelDocument(
    val id: IntGenericId,
    val title: String,
    val content: RawMessageContent,
    val visibility: String,
    val tags: String,
    val createdAt: Timestamp,
    val modifiedAt: Timestamp,
    val modifiedBy: GenericId,
    val teamId: GenericId,
    val gameId: Int?,
    val createdBy: GenericId,
    val isPublic: Boolean,
    val isCredentialed: Boolean,
    val isDraft: Boolean,
    val channelId: UniqueId
)

@Serializable
public data class RawChannelMedia(
    val id: IntGenericId,
    val type: String,
    val src: String,
    val title: String,
    val description: String?,
    val tags: List<String>,
    val visibility: String,
    val createdAt: Timestamp,
    val socialLinkSource: String?,
    val serviceId: Int?,
    //val additionalInfo: *?,
    val teamId: GenericId,
    val createdBy: GenericId,
    val gameId: Int?,
    val isPublic: Boolean,
    val srcThumbnail: String?,
    val channelId: UniqueId
)

@Serializable
public data class RawChannelReplyProperties(
    val isLocked: OptionalProperty<Boolean> = OptionalProperty.NotPresent,
    val isSticky: OptionalProperty<Boolean> = OptionalProperty.NotPresent
)