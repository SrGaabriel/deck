package com.deck.rest.entity

import com.deck.common.entity.RawGroup
import com.deck.common.entity.RawTeamMember
import com.deck.common.entity.RawTeamPaymentInfo
import com.deck.common.util.*
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
public data class RawFetchedTeam(
    val id: GenericId,
    val name: String,
    val subdomain: String,
    val bio: String?,
    // val status: Unit?,
    val timezone: String,
    val description: String?,
    val type: String,
    val games: List<Int>,
    // val characteristics: Unit?,
    val measurements: RawFetchedTeamMeasurements,
    val members: List<RawTeamMember>,
    val memberCount: Int?,
    val webhooks: List<RawTeamWebhook>,
    // Unknown if this is really present:
    // val bots: List<RawBot>,
    @DeckUnknown
    val rolesVersion: Int,
    val createdAt: Instant,
    val ownerId: GenericId,
    val profilePicture: String?,
    val teamDashImage: String?,
    val discordGuildId: Long?,
    val discordServerName: String?,
    val homeBannerImageSm: String?,
    val homeBannerImageMd: String?,
    val homeBannerImageLg: String?,
    val isRecruiting: Boolean,
    val isVerified: Boolean,
    val isPublic: Boolean,
    val alwaysShowTeamHome: Boolean,
    val isPro: Boolean,
    val autoSyncDiscordRoles: Boolean,
    val baseGroup: RawGroup,
    val followerCount: Int,
    val userFollowsTeam: Boolean,
    val isUserApplicant: Boolean,
    val isUserInvited: Boolean,
    val isUserBannedFromTeam: Boolean,
    val serverSubscriptionGateEnabled: OptionalProperty<Boolean> = OptionalProperty.NotPresent,
    val teamPaymentInfo: RawTeamPaymentInfo,
)

@Serializable
public data class RawFetchedTeamMeasurements(
    val numMembers: Int,
    val numFollowers: Int,
    val numRecentMatches: Int,
    val numRecentMatchesWins: OptionalProperty<Int> = OptionalProperty.NotPresent,
    val numFollowersAndMembers: Int,
    val numMembersAddedInLastDay: Int,
    val numMembersAddedInLastWeek: Int,
    val mostRecentMemberLastOnline: Long,
    val numMembersAddedInLastMonth: Int,
    val subscriptionMonthsRemaining: Int?
)

@Serializable
public data class RawFetchedMember(
    val id: GenericId,
    val name: String,
    val profilePicture: OptionalProperty<String> = OptionalProperty.NotPresent,
    val profileBannerBlur: String?,
    val nickname: OptionalProperty<String> = OptionalProperty.NotPresent,
    val roleIds: List<IntGenericId>
)

@Serializable
public data class RawTeamWebhook(
    val id: UniqueId,
    val name: String,
    val teamId: GenericId,
    val iconUrl: String?,
    val createdBy: GenericId,
    val createdAt: Instant,
    val deletedAt: Instant?
)