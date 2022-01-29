package com.deck.gateway.event.type

import com.deck.common.util.GenericId
import com.deck.gateway.entity.RawTeamMemberInfo
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("TeamMemberUpdated")
public data class GatewayTeamMemberUpdatedEvent(
    val serverId: GenericId,
    val userInfo: RawTeamMemberInfo
): GatewayEvent()