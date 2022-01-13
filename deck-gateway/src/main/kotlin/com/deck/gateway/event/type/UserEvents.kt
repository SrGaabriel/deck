package com.deck.gateway.event.type

import com.deck.common.entity.RawUserPresenceStatus
import com.deck.common.util.DeckExperimental
import com.deck.common.util.GenericId
import com.deck.common.util.OptionalProperty
import com.deck.common.util.UniqueId
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@DeckExperimental
@SerialName("UserStreamsVisibilityUpdated")
data class GatewayUserStreamsVisibilityUpdatedEvent(
    val teamId: GenericId,
    val channelId: OptionalProperty<UniqueId> = OptionalProperty.NotPresent,
    val groupId: OptionalProperty<GenericId> = OptionalProperty.NotPresent
): GatewayEvent()

@Serializable
@SerialName("USER_TEAMS_UPDATED")
data class GatewayUserTeamsUpdated(
    val teamId: GenericId,
    val isUserBannedFromTeam: Boolean,
    val isRemoved: Boolean
): GatewayEvent()

@Serializable
@SerialName("USER_PINGED")
@Deprecated("This will only be called if you open the guilded browser/app, it won't be called if you're running terminal only.")
data class GatewaySelfUserPingedEvent(
    val guildedClientId: UniqueId
): GatewayEvent()

@Serializable
@SerialName("USER_TEAM_SECTION_SEEN")
@Deprecated("This will only be called if you open the guilded browser/app, it won't be called if you're running terminal only.")
data class GatewayUserTeamSectionSeen(
    val itemId: UniqueId,
    val teamId: GenericId,
    val guildedClientId: UniqueId
): GatewayEvent()

@Serializable
@SerialName("USER_PRESENCE_MANUALLY_SET")
data class GatewayUserPresenceManuallySetEvent(
    val status: RawUserPresenceStatus
): GatewayEvent()