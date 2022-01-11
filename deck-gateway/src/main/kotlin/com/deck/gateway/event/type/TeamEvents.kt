package com.deck.gateway.event.type

import com.deck.common.entity.RawRole
import com.deck.common.util.*
import com.deck.gateway.entity.RawTeamMemberRoleId
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.Serializable

@Serializable
data class GatewayTeamXpAddedEvent(
    val type: String,
    val userIds: List<GenericId>,
    val amount: Int,
    val teamId: GenericId
): GatewayEvent()

@Serializable
data class GatewayTeamRolesUpdatedEvent(
    val type: String,
    val teamId: GenericId,
    val rolesById: Dictionary<String, RawRole>?,
    val isDelete: Boolean,
    val memberRoleIds: OptionalProperty<List<RawTeamMemberRoleId>> = OptionalProperty.NotPresent
): GatewayEvent()