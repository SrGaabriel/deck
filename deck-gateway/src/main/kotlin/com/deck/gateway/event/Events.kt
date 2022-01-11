package com.deck.gateway.event

import com.deck.common.entity.RawChannel
import com.deck.common.entity.RawChannelContentType
import com.deck.common.entity.RawChannelType
import com.deck.common.entity.RawMessageMentionedUserInfo
import com.deck.common.util.*
import com.deck.gateway.entity.RawPartialReceivedMessage
import com.deck.gateway.entity.RawPartialRepliedMessage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

abstract class GatewayEvent {
    var gatewayId: Int = 0
}

@Serializable
data class GatewayHelloEvent(
    @SerialName("sid") val sessionId: String,
    @DeckUnknown val upgrades: List<Unit>,
    val pingInterval: Long,
    val pingTimeout: Long
): GatewayEvent()

@Serializable
data class GatewayChatMessageCreateEvent(
    val type: String,
    val guildedClientId: UniqueId,
    val channelId: UniqueId,
    val channelCategoryId: OptionalProperty<UniqueId?> = OptionalProperty.NotPresent,
    val channelType: RawChannelType,
    val teamId: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    val contentType: RawChannelContentType,
    val message: RawPartialReceivedMessage,
    val repliedToMessages: List<RawPartialRepliedMessage>,
    val createdAt: Timestamp,
    val contentId: UniqueId,
    val createdBy: GenericId,
    val mentionedUserInfo: OptionalProperty<RawMessageMentionedUserInfo> = OptionalProperty.NotPresent
): GatewayEvent()

@Serializable
data class GatewayChannelTypingEvent(
    val type: String,
    val channelId: UniqueId,
    val userId: GenericId
): GatewayEvent()

@Serializable
data class GatewayTeamXpAddedEvent(
    val type: String,
    val userIds: List<GenericId>,
    val amount: Int,
    val teamId: GenericId
): GatewayEvent()

@Serializable
data class GatewayTeamChannelCreated(
    val type: String,
    val channel: RawChannel,
    val name: String,
    val guildedClientId: String,
    val teamId: GenericId
): GatewayEvent()

@Serializable
data class GatewayTeamChannelDeleted(
    val type: String,
    val channelId: UniqueId,
    val guildedClientId: UniqueId,
    val teamId: UniqueId
): GatewayEvent()