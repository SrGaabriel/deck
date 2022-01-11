package com.deck.gateway.event.type

import com.deck.common.entity.RawChannelContentType
import com.deck.common.util.DeckExperimental
import com.deck.common.util.GenericId
import com.deck.common.util.UniqueId
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.Serializable

@Serializable
data class GatewayChannelTypingEvent(
    val type: String,
    val channelId: UniqueId,
    val userId: GenericId
): GatewayEvent()

// Probably when a channel you aren't reading is pinged
@Serializable
@DeckExperimental
data class GatewayChannelBadgedEvent(
    val type: String,
    val contentType: RawChannelContentType,
    val teamId: GenericId,
    val channelId: UniqueId,
    val contentId: String,
    val createdAt: Long
): GatewayEvent()