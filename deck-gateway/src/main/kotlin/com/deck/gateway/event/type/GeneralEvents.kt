package com.deck.gateway.event.type

import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("HelloEvent")
public data class GatewayHelloEvent(
    val heartbeatIntervalMs: Long,
    val lastMessageId: String
) : GatewayEvent()