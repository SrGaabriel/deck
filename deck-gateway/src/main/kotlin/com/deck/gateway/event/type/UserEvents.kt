package com.deck.gateway.event.type

import com.deck.common.util.GenericId
import com.deck.common.util.OptionalProperty
import com.deck.common.util.UniqueId
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.Serializable

@Serializable
data class GatewayUserStreamsVisibilityUpdatedEvent(
    val type: String,
    val teamId: GenericId,
    val channelId: OptionalProperty<UniqueId> = OptionalProperty.NotPresent,
    val groupId: OptionalProperty<GenericId> = OptionalProperty.NotPresent
): GatewayEvent()