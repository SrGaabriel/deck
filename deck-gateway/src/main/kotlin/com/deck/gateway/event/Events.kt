package com.deck.gateway.event

import com.deck.common.entity.RawChannel
import com.deck.common.util.DeckUnknown
import com.deck.common.util.GenericId
import com.deck.common.util.UniqueId
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