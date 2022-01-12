package com.deck.gateway.event.type

import com.deck.common.entity.RawEmoji
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("teamReactionsUpdated")
data class GatewayTeamReactionsUpdatedEvent(
    val reactions: RawEmoji,
    val teamId: GenericId
): GatewayEvent()

@Serializable
@SerialName("teamReactionRemoved")
data class GatewayTeamReactionRemovedEvent(
    val reactionId: IntGenericId,
    val teamId: GenericId
): GatewayEvent()