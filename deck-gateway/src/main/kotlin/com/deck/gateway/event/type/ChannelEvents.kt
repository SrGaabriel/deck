package com.deck.gateway.event.type

import com.deck.common.entity.*
import com.deck.common.util.*
import com.deck.gateway.com.deck.gateway.entity.*
import com.deck.gateway.entity.RawUserIdObject
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
    val teamId: GenericId,
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