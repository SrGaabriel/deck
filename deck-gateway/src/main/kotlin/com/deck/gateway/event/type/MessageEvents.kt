package com.deck.gateway.event.type

import com.deck.common.entity.*
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.OptionalProperty
import com.deck.common.util.UniqueId
import com.deck.gateway.entity.RawMessageIdObject
import com.deck.gateway.entity.RawPartialDeletedMessage
import com.deck.gateway.entity.RawPartialEditedMessage
import com.deck.gateway.entity.RawPartialReceivedMessage
import com.deck.gateway.event.GatewayEvent
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param guildedClientId absent on system messages
 * @param channelCategoryId absent when channel isn't present ina category
 * @param teamId absent when message wasn't sent in a team
 * @param mentionedUserInfo absent when no one was mentioned.
 */
@Serializable
@SerialName("ChatMessageCreated")
public data class GatewayChatMessageCreatedEvent(
    val guildedClientId: OptionalProperty<UniqueId> = OptionalProperty.NotPresent,
    val channelId: UniqueId,
    val channelCategoryId: OptionalProperty<IntGenericId?> = OptionalProperty.NotPresent,
    val channelType: RawChannelType,
    val teamId: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    val contentType: RawChannelContentType,
    val message: RawPartialReceivedMessage,
    val repliedToMessages: List<RawPartialRepliedMessage>,
    val createdAt: Instant,
    val contentId: UniqueId,
    val createdBy: GenericId,
    val mentionedUserInfo: OptionalProperty<RawMessageMentionedUserInfo> = OptionalProperty.NotPresent
) : GatewayEvent()

@Serializable
@SerialName("ChatMessageUpdated")
public data class GatewayChatMessageUpdatedEvent(
    val guildedClientId: UniqueId,
    val channelId: UniqueId,
    val channelCategoryId: IntGenericId?,
    val channelType: RawChannelType,
    val teamId: GenericId?,
    val contentType: RawChannelContentType?,
    val updatedBy: GenericId,
    val contentId: UniqueId,
    val message: RawPartialEditedMessage
) : GatewayEvent()

@Serializable
@SerialName("ChatMessageDeleted")
public data class GatewayChatMessageDeleteEvent(
    val channelId: UniqueId,
    val channelCategoryId: IntGenericId?,
    val channelType: RawChannelType,
    val teamId: GenericId?,
    val contentType: RawChannelContentType,
    val message: RawPartialDeletedMessage,
    val newLastMessage: com.deck.common.entity.RawPartialDeletedMessage?
) : GatewayEvent()

/** Parameters [channelCategoryId] and [teamId] are missing when reaction is added in private channel */
@Serializable
@SerialName("ChatMessageReactionAdded")
public data class GatewayChatMessageReactionAddedEvent(
    val guildedClientId: OptionalProperty<UniqueId> = OptionalProperty.NotPresent,
    val channelId: UniqueId,
    val channelCategoryId: OptionalProperty<IntGenericId?> = OptionalProperty.NotPresent,
    val channelType: RawChannelType,
    val teamId: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    val contentType: RawChannelContentType,
    val reaction: RawReaction,
    val message: RawMessageIdObject
) : GatewayEvent()

/** Parameters [channelCategoryId] and [teamId] are missing when reaction is added in private channel */
@Serializable
@SerialName("ChatMessageReactionDeleted")
public data class GatewayChatMessageReactionDeletedEvent(
    val guildedClientId: OptionalProperty<UniqueId> = OptionalProperty.NotPresent,
    val channelId: UniqueId,
    val channelCategoryId: OptionalProperty<IntGenericId?> = OptionalProperty.NotPresent,
    val channelType: RawChannelType,
    val teamId: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    val contentType: RawChannelContentType,
    val reaction: RawReaction,
    val message: RawMessageIdObject
) : GatewayEvent()

@Serializable
@SerialName("ChatPinnedMessageCreated")
public data class GatewayChatPinnedMessageCreatedEvent(
    val guildedClientId: OptionalProperty<UniqueId> = OptionalProperty.NotPresent,
    val channelId: UniqueId,
    val channelCategoryId: IntGenericId?,
    val channelType: RawChannelType,
    val teamId: GenericId,
    val contentType: RawChannelContentType,
    val updatedBy: GenericId,
    val message: RawMessageIdObject,
    val userId: GenericId
) : GatewayEvent()

@Serializable
@SerialName("ChatPinnedMessageDeleted")
public data class GatewayChatPinnedMessageDeletedEvent(
    val guildedClientId: OptionalProperty<UniqueId> = OptionalProperty.NotPresent,
    val channelId: UniqueId,
    val channelCategoryId: IntGenericId?,
    val channelType: RawChannelType,
    val teamId: GenericId,
    val contentType: RawChannelContentType,
    val updatedBy: GenericId,
    val message: RawMessageIdObject,
) : GatewayEvent()