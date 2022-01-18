package com.deck.gateway.event.type

import com.deck.common.entity.*
import com.deck.common.util.*
import com.deck.gateway.entity.RawPartialBot
import com.deck.gateway.entity.RawTeamInfo
import com.deck.gateway.entity.RawTeamMemberRoleId
import com.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("TeamUpdated")
public data class GatewayTeamUpdatedEvent(
    val teamInfo: RawTeamInfo,
    val teamId: GenericId
) : GatewayEvent()

@Serializable
@SerialName("TeamXpAdded")
public data class GatewayTeamXpAddedEvent(
    val userIds: List<GenericId>,
    val amount: Int,
    val teamId: GenericId
) : GatewayEvent()

@Serializable
/**
 * Parameters [amount] and [guildedClientId] are absent when member leaves the guild
 */
public data class GatewayTeamXpSetEvent(
    val userIds: List<GenericId>,
    val amount: OptionalProperty<Int> = OptionalProperty.NotPresent,
    val teamId: GenericId,
    val guildedClientId: OptionalProperty<UniqueId> = OptionalProperty.NotPresent
) : GatewayEvent()

@Serializable
@SerialName("teamRolesUpdated")
public data class GatewayTeamRolesUpdatedEvent(
    val teamId: GenericId,
    val rolesById: Dictionary<String, RawRole>?,
    val isDelete: Boolean,
    val memberRoleIds: OptionalProperty<List<RawTeamMemberRoleId>> = OptionalProperty.NotPresent
) : GatewayEvent()

@Serializable
@SerialName("TeamMessagesDeleted")
public data class GatewayTeamMessagesDeletedEvent(
    val deletedContent: List<RawPartialDeletedMessage>,
    val bannedUserId: GenericId,
    val teamId: GenericId
) : GatewayEvent()

@Serializable
@SerialName("TeamBotCreated")
public data class GatewayTeamBotCreatedEvent(
    val userId: GenericId,
    val bot: RawBot,
    val guildedClientId: UniqueId,
    val teamId: GenericId
) : GatewayEvent()

@Serializable
@SerialName("TeamBotUpdated")
public data class GatewayTeamBotUpdatedEvent(
    val userId: GenericId,
    val bot: RawPartialBot,
    val guildedClientId: UniqueId,
    val teamId: GenericId
) : GatewayEvent()

@Serializable
@SerialName("TeamPaymentInfoUpdated")
public data class GatewayTeamPaymentInfoUpdatedEvent(
    val guildedClientId: UniqueId,
    val teamId: GenericId,
    val teamPaymentInfo: RawTeamPaymentInfo
) : GatewayEvent()

@Serializable
@SerialName("TeamServerSubscriptionPlanCreated")
public data class GatewayTeamServerSubscriptionPlanCreatedEvent(
    val guildedClientId: UniqueId,
    val teamId: GenericId,
    val serverSubscriptionPlan: RawTeamSubscriptionPlan
) : GatewayEvent()

@Serializable
@SerialName("TeamServerSubscriptionPlanUpdated")
public data class GatewayTeamServerSubscriptionPlanUpdatedEvent(
    val guildedClientId: UniqueId,
    val teamId: GenericId,
    val serverSubscriptionPlan: RawTeamSubscriptionPlan
) : GatewayEvent()

@Serializable
@SerialName("TeamServerSubscriptionPlanDeleted")
public data class GatewayTeamServerSubscriptionPlanDeletedEvent(
    val guildedClientId: UniqueId,
    val teamId: GenericId,
    val serverSubscriptionPlanId: UniqueId
) : GatewayEvent()

@Serializable
@SerialName("TeamGameAdded")
public data class GatewayTeamGameAddedEvent(
    @SerialName("gameId") val game: GameStatus,
    val teamId: GenericId
) : GatewayEvent()

@Serializable
@SerialName("teamContentReactionsAdded")
public data class GatewayTeamContentReactionsAddedEvent(
    val contentType: String, // warning: this isn't the channel's content type
    val contentId: IntGenericId,
    val userId: GenericId,
    val customReactionId: IntGenericId,
    val teamId: GenericId,
    val customReaction: RawCustomReaction
) : GatewayEvent()

@Serializable
@SerialName("teamContentReactionsRemoved")
public data class GatewayTeamContentReactionsRemovedEvent(
    val contentType: String, // warning: this isn't the channel's content type
    val contentId: IntGenericId,
    val userId: GenericId,
    val customReactionId: IntGenericId,
    val teamId: GenericId,
    val customReaction: RawCustomReaction
) : GatewayEvent()