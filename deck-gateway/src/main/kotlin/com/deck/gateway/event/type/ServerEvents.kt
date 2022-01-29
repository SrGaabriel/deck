package com.deck.gateway.event.type

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("TeamXpAdded")
public data class GatewayServerXpAddedEvent(
    public val userIds: List<GenericId>,
    public val amount: Int,
    public val serverId: GenericId
): GatewayEvent()

@Serializable
@SerialName("teamRolesUpdated")
public data class GatewayServerRolesUpdatedEvent(
    val serverId: GenericId,
    val memberRoleIds: List<IntGenericId>
): GatewayEvent()