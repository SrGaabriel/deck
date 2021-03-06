package io.github.deck.gateway.event.type

import io.github.deck.common.entity.RawMessage
import io.github.deck.common.util.GenericId
import io.github.deck.common.util.OptionalProperty
import io.github.deck.gateway.entity.RawPartialDeletedMessage
import io.github.deck.gateway.entity.RawUpdatedMessageReaction
import io.github.deck.gateway.event.GatewayEvent
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

@Serializable
@SerialName("ChannelMessageReactionCreated")
public data class GatewayChatMessageReactionCreatedEvent(
    val serverId: OptionalProperty<String> = OptionalProperty.NotPresent,
    val reaction: RawUpdatedMessageReaction
): GatewayEvent()

@Serializable
@SerialName("ChannelMessageReactionDeleted")
public data class GatewayChatMessageReactionDeletedEvent(
    val serverId: OptionalProperty<String> = OptionalProperty.NotPresent,
    val reaction: RawUpdatedMessageReaction
): GatewayEvent()