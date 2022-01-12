package com.deck.gateway.event.type

import com.deck.common.entity.RawGroup
import com.deck.common.util.GenericId
import com.deck.common.util.UniqueId
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// The parameters are the same, so why not just make update a typealias?
typealias GatewayTeamGroupUpdatedEvent = GatewayTeamGroupCreatedEvent
// Again, both events have the same parameters
typealias GatewayTeamGroupRestoredEvent = GatewayTeamGroupArchivedEvent

@Serializable
@SerialName("TeamGroupCreated")
data class GatewayTeamGroupCreatedEvent(
    val group: RawGroup,
    val teamId: GenericId,
    val guildedClientId: UniqueId
): GatewayEvent()

@Serializable
@SerialName("TeamGroupArchived")
data class GatewayTeamGroupArchivedEvent(
    val teamId: GenericId,
    val groupId: GenericId,
    val guildedClientId: UniqueId
): GatewayEvent()