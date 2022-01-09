package com.deck.gateway.payload

import com.deck.common.util.DeckUnknown
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GatewayHelloPayload(
    @SerialName("sid") val sessionId: String,
    @DeckUnknown val upgrades: List<Unit>,
    val pingInterval: Long,
    val pingTimeout: Long
)