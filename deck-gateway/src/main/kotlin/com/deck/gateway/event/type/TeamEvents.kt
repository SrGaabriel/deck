package com.deck.gateway.event.type

import com.deck.common.entity.RawBot
import com.deck.common.entity.RawChannelContentType
import com.deck.common.entity.RawChannelType
import com.deck.common.entity.RawRole
import com.deck.common.util.*
import com.deck.gateway.com.deck.gateway.entity.RawPartialBot
import com.deck.gateway.entity.RawTeamMemberRoleId
import com.deck.gateway.entity.RawUserIdObject
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("TeamXpAdded")
data class GatewayTeamXpAddedEvent(
    val userIds: List<GenericId>,
    val amount: Int,
    val teamId: GenericId
): GatewayEvent()

@Serializable
/**
 * Parameters [amount] and [guildedClientId] are absent when member leaves the guild
 */
data class GatewayTeamXpSetEvent(
    val userIds: List<GenericId>,
    val amount: OptionalProperty<Int> = OptionalProperty.NotPresent,
    val teamId: GenericId,
    val guildedClientId: OptionalProperty<UniqueId> = OptionalProperty.NotPresent
): GatewayEvent()

@Serializable
@SerialName("teamRolesUpdated")
data class GatewayTeamRolesUpdatedEvent(
    val teamId: GenericId,
    val rolesById: Dictionary<String, RawRole>?,
    val isDelete: Boolean,
    val memberRoleIds: OptionalProperty<List<RawTeamMemberRoleId>> = OptionalProperty.NotPresent
): GatewayEvent()

@Serializable
@SerialName("TeamChannelStreamRemoved")
data class GatewayTeamChannelStreamRemovedEvent(
    val channelId: UniqueId,
    val channelCategoryId: IntGenericId?,
    val channelType: RawChannelType,
    val teamId: GenericId,
    val contentType: RawChannelContentType,
    val user: RawUserIdObject
): GatewayEvent()

@Serializable
@SerialName("TeamChannelStreamEnded")
data class GatewayTeamChannelStreamEndedEvent(
    val channelId: UniqueId,
    val channelCategoryId: IntGenericId?,
    val channelType: RawChannelType,
    val teamId: GenericId,
    val contentType: RawChannelContentType,
    val groupId: GenericId,
    // val userStreams: List<*>
): GatewayEvent()

@Serializable
@SerialName("TeamBotCreated")
data class GatewayTeamBotCreatedEvent(
    val userId: GenericId,
    val bot: RawBot,
    val guildedClientId: UniqueId,
    val teamId: GenericId
): GatewayEvent()

@Serializable
@SerialName("TeamBotUpdated")
data class GatewayTeamBotUpdatedEvent(
    val userId: GenericId,
    val bot: RawPartialBot,
    val guildedClientId: UniqueId,
    val teamId: GenericId
): GatewayEvent()