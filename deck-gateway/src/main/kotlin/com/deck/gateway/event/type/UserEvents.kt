package com.deck.gateway.event.type

import com.deck.common.entity.RawUserAboutInfo
import com.deck.common.entity.RawUserPresenceStatus
import com.deck.common.util.DeckExperimental
import com.deck.common.util.GenericId
import com.deck.common.util.OptionalProperty
import com.deck.common.util.UniqueId
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("USER_UPDATED")
public data class GatewayUserUpdatedEvent(
    val name: OptionalProperty<String> = OptionalProperty.NotPresent,
    val avatar: OptionalProperty<String> = OptionalProperty.NotPresent,
    val subdomain: OptionalProperty<String> = OptionalProperty.NotPresent,
    val aboutInfo: OptionalProperty<RawUserAboutInfo> = OptionalProperty.NotPresent,
) : GatewayEvent()

@Serializable
@DeckExperimental
@SerialName("UserStreamsVisibilityUpdated")
public data class GatewayUserStreamsVisibilityUpdatedEvent(
    val teamId: GenericId,
    val channelId: OptionalProperty<UniqueId> = OptionalProperty.NotPresent,
    val groupId: OptionalProperty<GenericId> = OptionalProperty.NotPresent
) : GatewayEvent()

@Serializable
@SerialName("USER_TEAMS_UPDATED")
public data class GatewayUserTeamsUpdated(
    val teamId: GenericId,
    // Optional in application accept
    val isUserBannedFromTeam: OptionalProperty<Boolean> = OptionalProperty.NotPresent,
    // Optional in application accept
    val isRemoved: OptionalProperty<Boolean> = OptionalProperty.NotPresent
) : GatewayEvent()

@Serializable
@SerialName("USER_PINGED")
@Deprecated("This will only be called if you open the guilded browser/app, it won't be called if you're running terminal only.")
public data class GatewaySelfUserPingedEvent(
    val guildedClientId: UniqueId
) : GatewayEvent()

@Serializable
@SerialName("USER_TEAM_SECTION_SEEN")
@Deprecated("This will only be called if you open the guilded browser/app, it won't be called if you're running terminal only.")
public data class GatewayUserTeamSectionSeen(
    val itemId: UniqueId,
    val teamId: GenericId,
    val guildedClientId: UniqueId
) : GatewayEvent()

@Serializable
@SerialName("USER_PRESENCE_MANUALLY_SET")
public data class GatewayUserPresenceManuallySetEvent(
    val status: RawUserPresenceStatus
) : GatewayEvent()
