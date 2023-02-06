package io.github.srgaabriel.deck.gateway.event.type

import io.github.srgaabriel.deck.common.entity.RawMessage
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.gateway.entity.RawPartialDeletedMessage
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("ChatMessageCreated")
public data class GatewayChatMessageCreatedEvent(
    public val serverId: GenericId,
    public val message: RawMessage
): GatewayEvent()

@Serializable
@SerialName("ChatMessageUpdated")
public data class GatewayChatMessageUpdatedEvent(
    public val serverId: GenericId,
    public val message: RawMessage
): GatewayEvent()

@Serializable
@SerialName("ChatMessageDeleted")
public data class GatewayChatMessageDeletedEvent(
    public val serverId: GenericId,
    public val message: RawPartialDeletedMessage
): GatewayEvent()