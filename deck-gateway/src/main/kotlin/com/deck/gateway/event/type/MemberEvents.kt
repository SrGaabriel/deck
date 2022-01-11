package com.deck.gateway.event.type

import com.deck.common.util.DeckExperimental
import com.deck.common.util.GenericId
import com.deck.common.util.UniqueId
import com.deck.gateway.entity.RawTeamMemberUserInfo
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.Serializable

@Serializable
@DeckExperimental
data class GatewayTeamMemberUpdatedEvent(
    val type: String,
    val guildedClientId: UniqueId,
    val userId: GenericId,
    val updatedBy: GenericId,
    val userInfo: RawTeamMemberUserInfo
): GatewayEvent()