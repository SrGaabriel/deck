package com.deck.gateway.event.type

import com.deck.common.entity.RawChannel
import com.deck.common.util.GenericId
import com.deck.common.util.UniqueId
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.Serializable

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