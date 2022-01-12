package com.deck.gateway.event.type

import com.deck.common.entity.RawGroup
import com.deck.common.util.GenericId
import com.deck.common.util.UniqueId
import com.deck.gateway.com.deck.gateway.entity.RawGroupIdObject
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("TEAM_GROUP_CREATED")
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

@Serializable
@SerialName("TeamGroupUpdated")
data class GatewayTeamGroupUpdatedEvent(
    val group: RawGroup,
    val teamId: GenericId,
    val guildedClientId: UniqueId
): GatewayEvent()

@Serializable
@SerialName("TeamGroupRestored")
data class GatewayTeamGroupRestoredEvent(
    val teamId: GenericId,
    val groupId: GenericId,
    val guildedClientId: UniqueId
): GatewayEvent()

@Serializable
@SerialName("TeamGroupDeleted")
data class GatewayTeamGroupDeletedEvent(
    val groupId: GenericId,
    val teamId: GenericId
): GatewayEvent()

@Serializable
@SerialName("TEAM_USER_GROUP_PRIORITIES_UPDATED")
data class GatewayTeamUserGroupPrioritiesUpdated(
    val guildedClientId: UniqueId,
    val teamId: GenericId,
    val groups: List<RawGroupIdObject>
): GatewayEvent()