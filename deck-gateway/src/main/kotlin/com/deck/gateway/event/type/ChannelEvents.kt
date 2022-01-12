package com.deck.gateway.event.type

import com.deck.common.entity.RawChannel
import com.deck.common.entity.RawChannelCategory
import com.deck.common.entity.RawChannelContentType
import com.deck.common.util.*
import com.deck.gateway.com.deck.gateway.entity.RawTeamCategoryChannel
import com.deck.gateway.com.deck.gateway.entity.RawTeamCategoryChannelId
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.Serializable

@Serializable
data class GatewayTeamChannelCreatedEvent(
    val type: String,
    val channel: RawChannel,
    val name: String,
    val guildedClientId: UniqueId,
    val teamId: GenericId
): GatewayEvent()

@Serializable
data class GatewayTeamChannelDeletedEvent(
    val type: String,
    val channelId: UniqueId,
    val guildedClientId: UniqueId,
    val teamId: UniqueId
): GatewayEvent()

@Serializable
data class GatewayChannelTypingEvent(
    val type: String,
    val channelId: UniqueId,
    val userId: GenericId
): GatewayEvent()

// Probably when a channel you aren't reading is pinged
@Serializable
@DeckExperimental
data class GatewayChannelBadgedEvent(
    val type: String,
    val contentType: RawChannelContentType,
    val teamId: GenericId,
    val channelId: UniqueId,
    val contentId: String,
    val createdAt: Long
): GatewayEvent()

@Serializable
data class GatewayTeamChannelCategoryCreatedEvent(
    val type: String,
    val category: RawChannelCategory,
    val guildedClientId: UniqueId,
    val teamId: GenericId
): GatewayEvent()

/** Ironically, the boolean [isRoleUpdate] is missing when it's not a role update... */
@Serializable
data class GatewayTeamChannelCategoryUpdatedEvent(
    val type: String,
    val isRoleUpdate: OptionalProperty<Boolean> = OptionalProperty.NotPresent,
    val category: RawChannelCategory,
    val guildedClientId: UniqueId,
    val teamId: GenericId
): GatewayEvent()

@Serializable
data class GatewayTeamChannelCategoryGroupMovedEvent(
    val type: String,
    val groupId: GenericId,
    val channelCategoryId: IntGenericId,
    val channels: List<RawTeamCategoryChannelId>,
    val teamId: GenericId
): GatewayEvent()

@Serializable
data class GatewayTeamChannelCategoryDeletedEvent(
    val type: String,
    val channelCategoryId: IntGenericId,
    val channels: List<RawTeamCategoryChannel>,
    val teamId: GenericId
): GatewayEvent()