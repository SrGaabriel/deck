package com.deck.common.entity

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.OptionalProperty
import com.deck.common.util.UniqueId
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class RawTeam(
    val id: GenericId,
    val name: String,
    val subdomain: String?,
    val bio: OptionalProperty<String?> = OptionalProperty.NotPresent,
    val timezone: String?,
    val description: OptionalProperty<String> = OptionalProperty.NotPresent,
    val type: String?,
    val games: List<Int>,
    val profilePicture: String?,
    val ownerId: GenericId,
    val members: OptionalProperty<List<RawTeamMember>> = OptionalProperty.NotPresent,
    // val bots: List<*>,
    val webhooks: OptionalProperty<List<RawWebhook>> = OptionalProperty.NotPresent,
)

@Serializable
public data class RawTeamMember(
    val id: GenericId,
    val name: String,
    val nickname: String?,
    // val badges: List<RawBadge>,
    val membershipRole: String,
    val profilePicture: String?,
    val profileBannerBlur: String?,
    val joinDate: Instant,
    val userStatus: RawUserStatus,
    val userPresenceStatus: RawUserPresenceStatus,
    val userTransientStatus: OptionalProperty<RawTransientStatus?> = OptionalProperty.NotPresent,
    // val aliases: List<*>,
    val lastOnline: Instant,
    val roleIds: List<IntGenericId>?,
    val subscriptionType: OptionalProperty<Int?> = OptionalProperty.NotPresent,
    // val socialLinks: List<RawSocialLink>,
    val teamXp: Int
)

@Serializable
public data class RawBot(
    val id: UniqueId,
    val name: String,
    val enabled: Boolean,
    val flows: List<RawBotFlow>,
    val teamId: GenericId,
    val createdBy: GenericId,
    val createdAt: Instant,
    val deletedAt: Instant?,
    val userId: GenericId,
    val iconUrl: String?
)

@Serializable
public data class RawBotFlow(
    val id: UniqueId,
    val enabled: Boolean,
    val error: Boolean,
    val botId: UniqueId,
    val teamId: GenericId,
    val createdBy: GenericId,
    val createdAt: Instant,
    val deletedAt: Instant?,
    val triggerType: String,
    // val triggerMeta: RawBotFlowMeta,
    val actionType: String,
    // val actionMeta: RawBotFlowMeta
)

@Serializable
public data class RawBan(
    val reason: String,
    val userId: GenericId,
    val bannedBy: GenericId,
    val createdAt: Instant
)

@Serializable
public data class RawInvite(
    val id: GenericId,
    val createdAt: Instant,
    val teamId: GenericId,
    val invitedBy: GenericId,
    val userBy: GenericId?,
    @SerialName("gameId") val game: Int?,
    val useCount: Int
)

// This object is gateway exclusive, to be moved to gateway module later
@Serializable
public data class RawTeamPaymentInfo(
    val subscriptionsEnabled: OptionalProperty<Boolean> = OptionalProperty.NotPresent,
    val onboardingStripeAccountOnboardedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent,
)

@Serializable
public data class RawTeamSubscriptionPlan(
    val id: UniqueId,
    val name: String,
    val cost: Int,
    val description: String,
    val teamPaymentInfoId: UniqueId,
    val teamRoleId: IntGenericId,
    val subscriptionTier: String,
    val stripePriceId: String,
    val createdAt: Instant,
    val deletedAt: Instant?
)