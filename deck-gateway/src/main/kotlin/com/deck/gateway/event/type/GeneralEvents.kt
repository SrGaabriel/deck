package com.deck.gateway.event.type

import com.deck.common.util.DeckUnknown
import com.deck.common.util.GenericId
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class GatewayHelloEvent(
    @SerialName("sid") val sessionId: String,
    @DeckUnknown val upgrades: List<Unit>,
    val pingInterval: Long,
    val pingTimeout: Long
) : GatewayEvent()

@Serializable
@DeckUnknown
@SerialName("StageUpdated")
public data class GatewayStageUpdatedEvent(
    val userId: GenericId,
    val entityId: GenericId,
    val upsellType: String
): GatewayEvent()
