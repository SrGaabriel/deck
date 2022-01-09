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
    val nickname: String,
    // val badges: List<RawBadge>,
    val membershipRole: String,
    val profilePicture: String,
    val profileBannerBlur: String?,
    val joinDate: Timestamp,
    val userStatus: RawUserStatus,
    val userPresenceStatus: RawUserPresenceStatus,
    val userTransientStatus: RawTransientStatus,
    // val aliases: List<*>,
    val lastOnline: Timestamp,
    val roleIds: List<IntGenericId>,
    val subscriptionType: Int?,
    // val socialLinks: List<RawSocialLink>,
    val teamXp: Int
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