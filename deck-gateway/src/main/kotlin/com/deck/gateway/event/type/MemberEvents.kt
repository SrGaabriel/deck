package com.deck.gateway.event.type

import com.deck.common.entity.RawApplication
import com.deck.common.entity.RawTeamMember
import com.deck.common.util.*
import com.deck.gateway.com.deck.gateway.entity.RawPartialApplication
import com.deck.gateway.entity.RawTeamMemberUserInfo
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.Serializable

@Serializable
data class GatewayTeamMemberJoinedEvent(
    val type: String,
    val user: RawTeamMember,
    val teamId: GenericId
): GatewayEvent()

@Serializable
data class GatewayTeamMemberRemovedEvent(
    val type: String,
    val userId: GenericId,
    val teamId: GenericId
): GatewayEvent()

@Serializable
data class GatewayTeamApplicationCreatedEvent(
    val type: String,
    val teamId: GenericId,
    val application: RawApplication
): GatewayEvent()

@Serializable
data class GatewayTeamApplicationUpdatedEvent(
    val type: String,
    val teamId: GenericId,
    val applicationId: IntGenericId,
    val application: RawPartialApplication
): GatewayEvent()

@Serializable
data class GatewayTeamApplicationRemovedEvent(
    val type: String,
    val teamId: GenericId,
    val applicationId: IntGenericId
): GatewayEvent()

/** Incredibly enough, [guildedClientId] and [updatedBy] are missing when webhooks update other members */
@Serializable
@DeckExperimental
data class GatewayTeamMemberUpdatedEvent(
    val type: String,
    val guildedClientId: OptionalProperty<UniqueId> = OptionalProperty.NotPresent,
    val userId: GenericId,
    val updatedBy: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    val userInfo: RawTeamMemberUserInfo
): GatewayEvent()