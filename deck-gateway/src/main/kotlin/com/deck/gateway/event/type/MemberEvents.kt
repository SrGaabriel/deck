package com.deck.gateway.event.type

import com.deck.common.entity.RawApplication
import com.deck.common.entity.RawTeamMember
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.OptionalProperty
import com.deck.common.util.UniqueId
import com.deck.gateway.entity.RawPartialApplication
import com.deck.gateway.entity.RawTeamMemberUserInfo
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("TeamMemberJoined")
public data class GatewayTeamMemberJoinedEvent(
    val user: RawTeamMember,
    val teamId: GenericId
) : GatewayEvent()

@Serializable
@SerialName("TeamMemberRemoved")
public data class GatewayTeamMemberRemovedEvent(
    val userId: GenericId,
    val teamId: GenericId
) : GatewayEvent()

@Serializable
@SerialName("TeamApplicationCreated")
public data class GatewayTeamApplicationCreatedEvent(
    val teamId: GenericId,
    val application: RawApplication
) : GatewayEvent()

@Serializable
@SerialName("TeamApplicationUpdated")
public data class GatewayTeamApplicationUpdatedEvent(
    val teamId: GenericId,
    val applicationId: IntGenericId,
    val application: RawPartialApplication
) : GatewayEvent()

@Serializable
@SerialName("TeamApplicationRemoved")
public data class GatewayTeamApplicationRemovedEvent(
    val teamId: GenericId,
    val applicationId: IntGenericId
) : GatewayEvent()

/** Incredibly enough, [guildedClientId] and [updatedBy] are missing when webhooks update other members */
@Serializable
@SerialName("TeamMemberUpdated")
public data class GatewayTeamMemberUpdatedEvent(
    val guildedClientId: OptionalProperty<UniqueId> = OptionalProperty.NotPresent,
    val userId: GenericId,
    val updatedBy: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    val userInfo: RawTeamMemberUserInfo
) : GatewayEvent()

@Serializable
@SerialName("TeamMutedMembersUpdated")
public data class GatewayTeamMutedMembersUpdated(
    val isMuted: Boolean,
    val peerId: GenericId,
    val teamId: GenericId
) : GatewayEvent()

@Serializable
@SerialName("TeamDeafenedMembersUpdated")
public data class GatewayTeamDeafenedMembersUpdatedEvent(
    val isDeafened: Boolean,
    val peerId: GenericId,
    val teamId: GenericId
) : GatewayEvent()