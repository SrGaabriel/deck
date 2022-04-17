package io.github.deck.gateway.event.type

import io.github.deck.gateway.entity.RawBot
import io.github.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("HelloEvent")
public data class GatewayHelloEvent(
    val heartbeatIntervalMs: Long,
    val lastMessageId: String,
    @SerialName("user")
    val self: RawBot
) : GatewayEvent()