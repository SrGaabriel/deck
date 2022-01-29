package com.deck.gateway.event.type

import com.deck.common.util.GenericId
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("TeamXpAdded")
public data class GatewayServerXpAddeedEvent(
    public val userIds: List<GenericId>,
    public val amount: Int,
    public val serverId: GenericId
): GatewayEvent()