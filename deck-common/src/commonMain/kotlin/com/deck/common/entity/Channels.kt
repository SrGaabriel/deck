package com.deck.common.entity

import com.deck.common.util.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

/** @param description is null only when creating channels */
@Serializable
data class RawChannel(
    val id: UniqueId,
    val type: RawChannelType,
    val createdAt: Timestamp,
    val createdBy: GenericId,
    val updatedAt: Timestamp,
    val name: String,
    val contentType: RawChannelContentType,
    val archivedAt: Timestamp?,
    val parentChannelId: UniqueId?,
    val autoArchiveAt: Timestamp?,
    val deletedAt: Timestamp?,
    val archivedBy: GenericId?,
    val description: String?,
    val createdByWebhookId: UniqueId?,
    val archivedByWebhookId: UniqueId?,
    val teamId: GenericId?,
    val channelCategoryId: IntGenericId?,
    val addedAt: Timestamp?,
    val channelId: UniqueId?,
    val isRoleSynced: Boolean?,
    val roles: List<RawRole>?,
    val userPermissions: List<RawUserPermission>?,
    @DeckUnknown val tournamentRoles: List<Unit>? = null,
    val isPublic: Boolean,
    @DeckUnknown val priority: Int?,
    val groupId: GenericId?,
    @DeckUnknown val settings: Unit?,
    @DeckUnknown val groupType: String = "",
    val rolesById: Dictionary<String, RawRole>,
    @DeckUnknown val tournamentsRolesById: OptionalProperty<Unit?> = OptionalProperty.NotPresent,
    @DeckUnsupported val createdByInfo: OptionalProperty<Unit?> = OptionalProperty.NotPresent
)

/** @param voiceParticipants is not present on DMChatChannelCreated events */
@Serializable
data class RawPrivateChannel(
    val id: UniqueId,
    val type: RawChannelType,
    val createdAt: Timestamp,
    val createdBy: GenericId,
    val updatedAt: Timestamp,
    val name: String?,
    val description: String?,
    val lastMessage: RawPartialDeletedMessage,
    val contentType: RawChannelContentType,
    val archivedAt: Timestamp?,
    val autoArchiveAt: Timestamp?,
    val archivedBy: GenericId?,
    val parentChannelId: UniqueId?,
    val deletedAt: Timestamp?,
    val createdByWebhookId: UniqueId?,
    val archivedByWebhookId: UniqueId?,
    val dmType: String = "Default",
    val ownerId: GenericId,
    val voiceParticipants: OptionalProperty<List<RawUser>> = OptionalProperty.NotPresent,
    val users: List<RawUser>
)

@Serializable
data class RawChannelCategory(
    val id: IntGenericId,
    val name: String,
    val priority: Int?,
    val roles: List<RawRolePermissionsOverwritten>?,
    val rolesById: Dictionary<String, RawRolePermissionsOverwritten>,
    val teamId: GenericId,
    val createdAt: Timestamp,
    val updatedAt: Timestamp?,
    val groupId: GenericId,
    val channelCategoryId: IntGenericId?,
    val userPermissions: List<RawUserPermission>?
)

@Serializable
@OptIn(ExperimentalSerializationApi::class)
enum class RawChannelType {
    @SerialName("Team")
    @JsonNames("team")
    Team,
    @SerialName("DM") Private;
}

@Serializable
enum class RawChannelContentType {
    @SerialName("chat") Chat,
    @SerialName("stream") Streaming,
    @SerialName("voice") Voice,
    @SerialName("event") Calendar,
    @SerialName("scheduling") Scheduling,
    @SerialName("announcement") Announcements,
    @SerialName("forum") Forum,
    @SerialName("list") List,
    @SerialName("doc") Documentation,
    @SerialName("media") Media;
}

/** Parameters [gameId] and [teamId] are absent when reply is sent in a forum. */
@Serializable
data class RawChannelContentReply @OptIn(ExperimentalSerializationApi::class) constructor(
    val id: IntGenericId,
    val message: RawMessageContent,
    val createdAt: Timestamp,
    val editedAt: Timestamp?,
    val gameId: OptionalProperty<Int?> = OptionalProperty.NotPresent,
    val teamId: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    @JsonNames("contentId", "repliesTo")
    val contentId: IntGenericId,
    val createdBy: GenericId
)