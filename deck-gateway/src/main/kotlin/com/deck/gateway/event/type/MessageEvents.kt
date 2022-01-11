package com.deck.gateway.event.type

import com.deck.common.entity.RawChannelContentType
import com.deck.common.entity.RawChannelType
import com.deck.common.entity.RawMessageMentionedUserInfo
import com.deck.common.util.GenericId
import com.deck.common.util.OptionalProperty
import com.deck.common.util.Timestamp
import com.deck.common.util.UniqueId
import com.deck.gateway.entity.RawPartialDeletedMessage
import com.deck.gateway.entity.RawPartialReceivedMessage
import com.deck.gateway.entity.RawPartialRepliedMessage
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.Serializable

/**
 * @param guildedClientId absent on system messages
 * @param channelCategoryId absent when channel isn't present ina category
 * @param teamId absent when message wasn't sent in a team
 * @param mentionedUserInfo absent when no one was mentioned.
 */
@Serializable
data class GatewayChatMessageCreateEvent(
    val type: String,
    val guildedClientId: OptionalProperty<UniqueId> = OptionalProperty.NotPresent,
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
data class GatewayChatMessageDeleteEvent(
    val type: String,
    val channelId: UniqueId,
    val channelCategoryId: UniqueId?,
    val channelType: RawChannelType,
    val teamId: GenericId?,
    val contentType: RawChannelContentType,
    val message: RawPartialDeletedMessage,
    val newLastMessage: RawPartialRepliedMessage?
): GatewayEvent()