package com.deck.common.entity

import com.deck.common.util.*
import kotlinx.datetime.Instant
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

/** @param description is null only when creating channels */
@Serializable
public data class RawChannel(
    val id: UniqueId,
    val type: RawChannelType,
    val createdAt: Instant,
    val createdBy: GenericId,
    val updatedAt: Instant,
    val name: String,
    val contentType: RawChannelContentType,
    val archivedAt: Instant?,
    val parentChannelId: UniqueId?,
    val autoArchiveAt: Instant?,
    val deletedAt: Instant?,
    val archivedBy: GenericId?,
    val description: String?,
    val createdByWebhookId: UniqueId?,
    val archivedByWebhookId: UniqueId?,
    val teamId: GenericId?,
    val channelCategoryId: IntGenericId?,
    val addedAt: Instant?,
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
    val rolesById: Dictionary<String, RawRolePermissionsOverwritten>,
    @DeckUnknown val tournamentsRolesById: OptionalProperty<Unit?> = OptionalProperty.NotPresent,
    @DeckUnsupported val createdByInfo: OptionalProperty<Unit?> = OptionalProperty.NotPresent
)

/** @param voiceParticipants is not present on DMChatChannelCreated events */
@Serializable
public data class RawPrivateChannel(
    val id: UniqueId,
    val type: RawChannelType,
    val createdAt: Instant,
    val createdBy: GenericId,
    val updatedAt: Instant,
    val name: String?,
    val description: String?,
    val lastMessage: RawPartialDeletedMessage,
    val contentType: RawChannelContentType,
    val archivedAt: Instant?,
    val autoArchiveAt: Instant?,
    val archivedBy: GenericId?,
    val parentChannelId: UniqueId?,
    val deletedAt: Instant?,
    val createdByWebhookId: UniqueId?,
    val archivedByWebhookId: UniqueId?,
    val dmType: String = "Default",
    val ownerId: GenericId,
    val voiceParticipants: OptionalProperty<List<RawUser>> = OptionalProperty.NotPresent,
    val users: List<RawUser>
)

@Serializable
public data class RawChannelCategory(
    val id: IntGenericId,
    val name: String,
    val priority: Int?,
    val roles: List<RawRolePermissionsOverwritten>?,
    val rolesById: Dictionary<String, RawRolePermissionsOverwritten>,
    val teamId: GenericId,
    val createdAt: Instant,
    val updatedAt: Instant?,
    val groupId: GenericId,
    val channelCategoryId: IntGenericId?,
    val userPermissions: List<RawUserPermission>?
)

@Serializable
@OptIn(ExperimentalSerializationApi::class)
public enum class RawChannelType {
    @SerialName("Team")
    @JsonNames("team")
    Team,

    @SerialName("DM")
    Private;
}

@Serializable
public enum class RawChannelContentType {
    @SerialName("chat")
    Chat,

    @SerialName("stream")
    Streaming,

    @SerialName("voice")
    Voice,

    @SerialName("event")
    Calendar,

    @SerialName("scheduling")
    Scheduling,

    @SerialName("announcement")
    Announcements,

    @SerialName("forum")
    Forum,

    @SerialName("list")
    List,

    @SerialName("doc")
    Documentation,

    @SerialName("media")
    Media;
}

/** Parameters [gameId] and [teamId] are absent when reply is sent in a forum. */
@Serializable
public data class RawChannelContentReply @OptIn(ExperimentalSerializationApi::class) constructor(
    val id: IntGenericId,
    val message: RawMessageContent,
    val createdAt: Instant,
    val editedAt: Instant?,
    val gameId: OptionalProperty<Int?> = OptionalProperty.NotPresent,
    val teamId: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    @JsonNames("contentId", "repliesTo")
    val contentId: IntGenericId,
    val createdBy: GenericId
)
