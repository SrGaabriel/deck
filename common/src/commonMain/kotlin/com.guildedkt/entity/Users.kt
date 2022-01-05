package com.guildedkt.entity

import com.guildedkt.util.GenericId
import com.guildedkt.util.Timestamp
import com.guildedkt.util.TransientStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RawUser(
    val id: GenericId,
    val name: String,
    val subdomain: String?,
    val email: String?,
    val profilePicture: String,
    val profilePictureSm: String,
    val profilePictureLg: String,
    val profilePictureBlur: String,
    val profileBannerSm: String,
    val profileBannerLg: String,
    val profileBannerBlur: String,
    val joinDate: Timestamp,
    val steamId: String?,
    val userStatus: RawUserStatus,
//    val userPresenceStatus: RawUserPresenceStatus,
//    val aboutInfo: RawUserAboutInfo,
    val lastOnline: Timestamp,
    val stonks: Int?,
//    val flairInfos: List<RawUserFlair>
)

@Serializable
data class RawUserStatus(
    // TODO: Support
    val content: String? = null,
    val customReactionId: Int?,
    val customReaction: RawEmoji
)
