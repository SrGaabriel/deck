package com.guildedkt.entity

import com.guildedkt.util.GenericId
import com.guildedkt.util.Timestamp
import kotlinx.serialization.Serializable

@Serializable
data class RawTeam(
    val id: GenericId,
    val name: String,
    val subdomain: String?,
    val bio: String?,
    val profilePicture: String?,
    val ownerId: GenericId,
    val members: List<RawTeamMember>,
    // val bots: List<*>,
    val webhooks: List<RawWebhook>,
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
    val userTransientStatus: RawUser,
    // val aliases: List<*>,
    val lastOnline: Timestamp,
    val roleIds: List<Int>,
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
    val gameId: Int?,
    val useCount: Int
)