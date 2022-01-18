package com.deck.common.entity

import com.deck.common.util.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RawTeam(
    val id: GenericId,
    val name: String,
    val subdomain: String?,
    val bio: OptionalProperty<String?> = OptionalProperty.NotPresent,
    val timezone: String,
    val description: OptionalProperty<String> = OptionalProperty.NotPresent,
    val type: String,
    val games: List<GameStatus>,
    val profilePicture: String?,
    val ownerId: GenericId,
    val members: OptionalProperty<List<RawTeamMember>> = OptionalProperty.NotPresent,
    // val bots: List<*>,
    val webhooks: OptionalProperty<List<RawWebhook>> = OptionalProperty.NotPresent,
)

@Serializable
data class RawTeamMember(
    val id: GenericId,
    val name: String,
    val nickname: String?,
    // val badges: List<RawBadge>,
    val membershipRole: String,
    val profilePicture: String?,
    val profileBannerBlur: String?,
    val joinDate: Timestamp,
    val userStatus: RawUserStatus,
    val userPresenceStatus: RawUserPresenceStatus,
    val userTransientStatus: OptionalProperty<RawTransientStatus?> = OptionalProperty.NotPresent,
    // val aliases: List<*>,
    val lastOnline: Timestamp,
    val roleIds: List<IntGenericId>?,
    val subscriptionType: OptionalProperty<Int?> = OptionalProperty.NotPresent,
    // val socialLinks: List<RawSocialLink>,
    val teamXp: Int
)

@Serializable
data class RawBot(
    val id: UniqueId,
    val name: String,
    val enabled: Boolean,
    val flows: List<RawBotFlow>,
    val teamId: GenericId,
    val createdBy: GenericId,
    val createdAt: Timestamp,
    val deletedAt: Timestamp?,
    val userId: GenericId,
    val iconUrl: String?
)

@Serializable
data class RawBotFlow(
    val id: UniqueId,
    val enabled: Boolean,
    val error: Boolean,
    val botId: UniqueId,
    val teamId: GenericId,
    val createdBy: GenericId,
    val createdAt: Timestamp,
    val deletedAt: Timestamp?,
    val triggerType: String,
    // val triggerMeta: RawBotFlowMeta,
    val actionType: String,
    // val actionMeta: RawBotFlowMeta
)

@Serializable
data class RawBan(
    val reason: String,
    val userId: GenericId,
    val bannedBy: GenericId,
    val createdAt: Timestamp
)

@Serializable
data class RawInvite(
    val id: GenericId,
    val createdAt: Timestamp,
    val teamId: GenericId,
    val invitedBy: GenericId,
    val userBy: GenericId?,
    @SerialName("gameId") val game: GameStatus?,
    val useCount: Int
)

// This object is gateway exclusive, to be moved to gateway module later
@Serializable
data class RawTeamPaymentInfo(
    val subscriptionsEnabled: OptionalProperty<Boolean> = OptionalProperty.NotPresent,
    val onboardingStripeAccountOnboardedAt: OptionalProperty<Timestamp> = OptionalProperty.NotPresent,
)

@Serializable
data class RawTeamSubscriptionPlan(
    val id: UniqueId,
    val name: String,
    val cost: Int,
    val description: String,
    val teamPaymentInfoId: UniqueId,
    val teamRoleId: IntGenericId,
    val subscriptionTier: String,
    val stripePriceId: String,
    val createdAt: Timestamp,
    val deletedAt: Timestamp?
)
