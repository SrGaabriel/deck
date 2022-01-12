package com.deck.gateway.event.type

import com.deck.common.entity.RawChannel
import com.deck.common.entity.RawChannelCategory
import com.deck.common.entity.RawChannelContentType
import com.deck.common.util.*
import com.deck.gateway.com.deck.gateway.entity.RawTeamCategoryChannel
import com.deck.gateway.com.deck.gateway.entity.RawTeamCategoryChannelId
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

@Serializable
@SerialName("TeamChannelDeleted")
data class GatewayTeamChannelDeletedEvent(
    val channelId: UniqueId,
    val guildedClientId: UniqueId,
    val teamId: UniqueId
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
    val createdAt: Long
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