package com.deck.gateway.event.type

import com.deck.common.entity.*
import com.deck.common.util.*
import com.deck.gateway.com.deck.gateway.entity.*
import com.deck.gateway.entity.RawUserIdObject
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
@SerialName("TeamChannelCreated")
data class GatewayTeamChannelCreatedEvent(
    val channel: RawChannel,
    val name: String,
    val guildedClientId: UniqueId,
    val teamId: GenericId
): GatewayEvent()

/** Again, the [isRoleUpdate] parameter is missing when it's not a role update */
@Serializable
@SerialName("TeamChannelUpdated")
data class GatewayTeamChannelUpdatedEvent(
    val channel: RawPartialTeamChannel,
    val teamId: GenericId,
    val isRoleUpdate: OptionalProperty<Boolean> = OptionalProperty.NotPresent,
    val guildedClientId: UniqueId
): GatewayEvent()

@Serializable
@SerialName("TeamChannelDeleted")
data class GatewayTeamChannelDeletedEvent(
    val channelId: UniqueId,
    val guildedClientId: UniqueId,
    val teamId: UniqueId
): GatewayEvent()

// When wrapping, remember that this event can be called even if only one channel was deleted.
@Serializable
@SerialName("TeamChannelsDeleted")
data class GatewayTeamChannelsDeletedEvent(
    val channelIds: List<UniqueId>,
    val teamId: GenericId
): GatewayEvent()

@Serializable
@SerialName("ChatChannelTyping")
data class GatewayChannelTypingEvent(
    val channelId: UniqueId,
    val userId: GenericId
): GatewayEvent()

// Probably when a channel you aren't reading is pinged
@Serializable
@DeckExperimental
@SerialName("CHANNEL_BADGED")
data class GatewayChannelBadgedEvent(
    val contentType: RawChannelContentType,
    val teamId: GenericId?,
    val channelId: UniqueId,
    val contentId: String,
    val createdAt: Timestamp
): GatewayEvent()

@Serializable
@SerialName("TeamChannelCategoryCreated")
data class GatewayTeamChannelCategoryCreatedEvent(
    val category: RawChannelCategory,
    val guildedClientId: UniqueId,
    val teamId: GenericId
): GatewayEvent()

/** Ironically, the boolean [isRoleUpdate] is missing when it's not a role update... */
@Serializable
@SerialName("TeamChannelCategoryUpdated")
data class GatewayTeamChannelCategoryUpdatedEvent(
    val isRoleUpdate: OptionalProperty<Boolean> = OptionalProperty.NotPresent,
    val category: RawChannelCategory,
    val guildedClientId: UniqueId,
    val teamId: GenericId
): GatewayEvent()

@Serializable
@SerialName("TeamChannelCategoryDeleted")
data class GatewayTeamChannelCategoryDeletedEvent(
    val channelCategoryId: IntGenericId,
    val channels: List<RawTeamCategoryChannel>,
    val teamId: GenericId
): GatewayEvent()

@Serializable
@SerialName("TeamChannelCategoriesDeleted")
data class GatewayTeamChannelCategoriesDeletedEvent(
    val channelCategoryIds: List<IntGenericId>,
    val teamId: GenericId
): GatewayEvent()

@Serializable
@SerialName("TeamChannelCategoryGroupMoved")
data class GatewayTeamChannelCategoryGroupMovedEvent(
    val groupId: GenericId,
    val channelCategoryId: IntGenericId,
    val channels: List<RawTeamCategoryChannelId>,
    val teamId: GenericId
): GatewayEvent()

@Serializable
@SerialName("TeamChannelVoiceParticipantAdded")
data class GatewayTeamChannelVoiceParticipantAddedEvent(
    val channelId: UniqueId,
    val channelCategoryId: IntGenericId?,
    val channelType: RawChannelType,
    val teamId: GenericId?,
    val contentType: RawChannelContentType,
    val user: RawUserIdObject
): GatewayEvent()

@Serializable
@SerialName("TeamChannelVoiceParticipantRemoved")
data class GatewayTeamChannelVoiceParticipantRemovedEvent(
    val channelId: UniqueId,
    val channelCategoryId: IntGenericId?,
    val channelType: RawChannelType,
    val teamId: GenericId?,
    val contentType: RawChannelContentType,
    val user: RawUserIdObject
): GatewayEvent()

@Serializable
@SerialName("DMChatChannelCreated")
data class GatewayPrivateChatChannelCreatedEvent(
    val channelId: UniqueId,
    val channel: RawPrivateChannel
): GatewayEvent()

@Serializable
@SerialName("ChatChannelUpdated")
data class GatewayChatChannelUpdatedEvent(
    val channelId: UniqueId,
    val channelType: RawChannelType,
    val contentType: RawChannelContentType,
    val channel: OptionalProperty<RawPartialPrivateChannel> = OptionalProperty.NotPresent,
    val removedInfo: OptionalProperty<RawPrivateChannelRemovedInfo> = OptionalProperty.NotPresent
): GatewayEvent()

@Serializable
@SerialName("ChatChannelBroadcastCall")
data class GatewayChatChannelBroadcastCallEvent(
    val channelId: UniqueId,
    val participants: List<RawUserIdObject>,
    val callStartTime: Timestamp,
    val callerName: String,
    val callType: RawChannelContentType
): GatewayEvent()

@Serializable
@SerialName("ChatChannelBroadcastCallResponse")
data class GatewayChatChannelBroadcastCallResponseEvent(
    val channelId: UniqueId,
    val userId: GenericId,
    val callType: RawChannelContentType,
    val response: RawBroadcastCallResponse
): GatewayEvent()

@Serializable
@SerialName("TemporalChannelCreated")
data class GatewayTemporalChannelCreatedEvent(
    val channelId: UniqueId,
    val channelCategoryId: IntGenericId?,
    val channelType: RawChannelType,
    val teamId: GenericId,
    val contentType: RawChannelContentType,
    val channel: RawChannel,
    val silenceNotification: Boolean
): GatewayEvent()

@Serializable
@SerialName("TemporalChannelUsersAdded")
data class GatewayTemporalChannelUsersAddedEvent(
    val channelId: UniqueId,
    val channelCategoryId: IntGenericId?,
    val channelType: RawChannelType,
    val teamId: GenericId,
    val contentType: RawChannelContentType,
    val channel: RawChannel,
    val silenceNotification: Boolean
): GatewayEvent()

@Serializable
@SerialName("TeamChannelPrioritiesUpdated")
data class GatewayTeamChannelPrioritiesUpdatedEvent(
    val orderedChannelIds: List<UniqueId>
): GatewayEvent()

@Serializable
@SerialName("TeamChannelCategoryPrioritiesUpdated")
data class GatewayTeamChannelCategoryPrioritiesUpdatedEvent(
    val orderedChannelCategoryIds: List<IntGenericId>
): GatewayEvent()

@Serializable
@SerialName("CHANNEL_SEEN")
data class GatewaySelfChannelSeenEvent(
    val channelId: UniqueId,
    val teamId: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    val clearAllBadges: Boolean,
    val contentType: RawChannelContentType,
    val guildedClientId: UniqueId
): GatewayEvent()

@Serializable
@SerialName("TEAM_CHANNEL_CONTENT_CREATED")
data class GatewayTeamChannelContentCreatedEvent(
    val guildedClientId: UniqueId,
    val channelId: UniqueId,
    val channelCategoryId: IntGenericId?,
    val channelType: RawChannelType,
    val teamId: GenericId,
    val contentType: RawChannelContentType,
    val createdAt: Timestamp,
    val contentId: JsonElement,
    val createdBy: GenericId,
    val event: OptionalProperty<RawChannelEvent> = OptionalProperty.NotPresent,
    val isRepeating: OptionalProperty<Boolean> = OptionalProperty.NotPresent,
    val listItem: OptionalProperty<RawChannelListItem> = OptionalProperty.NotPresent,
    val thread: OptionalProperty<RawChannelForumThread> = OptionalProperty.NotPresent,
    val availability: OptionalProperty<RawChannelScheduleAvailability> = OptionalProperty.NotPresent,
    @SerialName("doc") val document: OptionalProperty<RawChannelDocument> = OptionalProperty.NotPresent,
    val media: OptionalProperty<RawChannelMedia> = OptionalProperty.NotPresent
): GatewayEvent()

/** Parameters [guildedClientId], [updatedBy] and [contentId] aren't present when editing reply or simple boolean properties (for example, the reply lock in forums) */
@Serializable
@SerialName("TEAM_CHANNEL_CONTENT_UPDATED")
data class GatewayTeamChannelContentUpdatedEvent(
    val guildedClientId: OptionalProperty<UniqueId> = OptionalProperty.NotPresent,
    val channelId: UniqueId,
    val channelCategoryId: IntGenericId?,
    val channelType: RawChannelType,
    val teamId: GenericId,
    val contentType: RawChannelContentType,
    val updatedBy: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    val contentId: OptionalProperty<JsonElement> = OptionalProperty.NotPresent,
    @Deprecated("Useless") val event: OptionalProperty<List<Unit>> = OptionalProperty.NotPresent,
    val isRepeating: OptionalProperty<Boolean> = OptionalProperty.NotPresent,
    val listItem: OptionalProperty<RawChannelListItem> = OptionalProperty.NotPresent,
    val thread: OptionalProperty<RawPartialChannelForumThread> = OptionalProperty.NotPresent,
    val availability: OptionalProperty<RawChannelScheduleAvailability> = OptionalProperty.NotPresent,
    @SerialName("doc") val document: OptionalProperty<RawChannelDocument> = OptionalProperty.NotPresent,
    val media: OptionalProperty<RawChannelMedia> = OptionalProperty.NotPresent,
    val reply: OptionalProperty<RawChannelReplyProperties> = OptionalProperty.NotPresent,
    val createdAt: OptionalProperty<Timestamp> = OptionalProperty.NotPresent
): GatewayEvent()

/**
 * @param createdAt is missing when forum topics are deleted (there could be more scenarios though)
 * @param guildedClientId is missing when docs are deleted (there could be more scenarios though)
 */
@Serializable
@SerialName("TEAM_CHANNEL_CONTENT_DELETED")
data class GatewayTeamChannelContentDeletedEvent(
    val guildedClientId: OptionalProperty<UniqueId> = OptionalProperty.NotPresent,
    val channelId: UniqueId,
    val channelCategoryId: IntGenericId?,
    val channelType: RawChannelType,
    val teamId: GenericId,
    val contentType: RawChannelContentType,
    val createdAt: OptionalProperty<Timestamp> = OptionalProperty.NotPresent,
    val deletedBy: GenericId,
    val contentId: JsonElement
): GatewayEvent()

@Serializable
@SerialName("TEAM_CHANNEL_ARCHIVED")
data class GatewayTeamChannelArchivedEvent(
    val channelId: UniqueId,
    val channelCategoryId: IntGenericId?,
    val channelType: RawChannelType,
    val teamId: GenericId,
    val contentType: RawChannelContentType,
    val archivedBy: GenericId,
    val silenceNotification: Boolean
): GatewayEvent()

@Serializable
@SerialName("TeamEventRemoved")
data class GatewayTeamEventRemovedEvent(
    val familyId: UniqueId,
    val guildedClientId: UniqueId,
    val teamId: GenericId
): GatewayEvent()

@Serializable
@SerialName("TeamChannelAvailabilitiesUpdated")
data class GatewayTeamChannelAvailabilitiesUpdatedEvent(
    val channelId: UniqueId,
    val availabilities: List<RawChannelScheduleAvailability>,
    val teamId: GenericId
): GatewayEvent()

@Serializable
@SerialName("TeamChannelAvailabilitiesRemoved")
data class GatewayTeamChannelAvailabilitiesRemovedEvent(
    val channelId: UniqueId,
    val availabilityId: IntGenericId,
    val teamId: GenericId
): GatewayEvent()

@Serializable
@SerialName("TEAM_CHANNEL_CONTENT_REPLY_CREATED")
data class GatewayTeamChannelContentReplyCreatedEvent(
    val guildedClientId: UniqueId,
    val channelId: UniqueId,
    val channelCategoryId: IntGenericId?,
    val channelType: RawChannelType,
    val teamId: GenericId,
    val contentType: RawChannelContentType,
    val contentId: GenericId,
    val createdAt: Timestamp,
    val createdBy: GenericId,
    val reply: RawChannelContentReply,
    val silenceNotification: Boolean
): GatewayEvent()

@Serializable
@SerialName("TEAM_CHANNEL_CONTENT_REPLY_UPDATED")
data class GatewayTeamChannelContentReplyUpdatedEvent(
    val guildedClientId: UniqueId,
    val channelId: UniqueId,
    val channelCategoryId: IntGenericId?,
    val channelType: RawChannelType,
    val teamId: GenericId,
    val contentType: RawChannelContentType,
    val updatedBy: GenericId,
    val contentId: GenericId,
    val isContentReply: Boolean,
    val createdBy: GenericId,
    val message: RawMessageContent
): GatewayEvent()

@Serializable
@SerialName("TEAM_CHANNEL_CONTENT_REPLY_DELETED")
data class GatewayTeamChannelContentReplyDeletedEvent(
    val channelId: UniqueId,
    val channelCategoryId: IntGenericId?,
    val channelType: RawChannelType,
    val teamId: GenericId,
    val contentType: RawChannelContentType,
    val createdAt: Timestamp,
    val deletedBy: GenericId,
    val contentId: GenericId,
    val contentReplyId: IntGenericId
): GatewayEvent()