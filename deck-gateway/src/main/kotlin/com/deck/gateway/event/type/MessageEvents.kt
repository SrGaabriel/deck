package com.deck.gateway.event.type

import com.deck.common.entity.RawMessage
import com.deck.common.util.GenericId
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("ChatMessageCreated")
public data class GatewayChatMessageCreatedEvent(
    public val serverId: GenericId,
    public val message: RawMessage
): GatewayEvent()