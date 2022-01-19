package com.deck.rest.request

import com.deck.common.entity.*
import com.deck.common.util.GenericId
import com.deck.common.util.OptionalProperty
import com.deck.common.util.RawUserPresenceType
import kotlinx.serialization.Serializable

@Serializable
public data class SelfUserResponse(
    val updateMessage: String?,
    val user: RawUser,
    val teams: List<RawTeam>,
    val customReactions: List<RawCustomReaction>,
    val customEmojis: OptionalProperty<List<GenericId>> = OptionalProperty.NotPresent,
    val landingUrl: Boolean,
    val friends: List<RawFriend>
)

@Serializable
public data class UserResponse(
    val user: RawUser
)

@Serializable
public data class ModifySelfUserRequest(
    var name: String?,
    var avatar: String?,
    var subdomain: String?,
    var aboutInfo: RawUserAboutInfo?
)

@Serializable
public data class UserDMResponse(
    val channels: List<RawPrivateChannel>
)

@Serializable
public data class CreateDMChannelRequest(
    val users: List<GenericId>
)

@Serializable
public data class SetSelfPresenceRequest(
    val status: RawUserPresenceStatus
)

@Serializable
public data class SetUserTransientStatusRequest(
    val id: Int,
    val gameId: Int?,
    val type: RawUserPresenceType
)

@Serializable
public data class SelfReferralStatisticsResponse(
    val referrals: Int,
    val stonks: Int,
    val requiredForNextStonk: Int
)

@Serializable
public data class SelfUpdateAvatarRequest(
    val imageUrl: String
)

@Serializable
public data class SelfUpdateAvatarResponse(
    val profilePictureSm: String,
    val profilePicture: String,
    val profilePictureLg: String
)

@Serializable
public data class SelfUpdateBannerResponse(
    val profileBannerSm: String,
    val profileBannerLg: String,
    val profileBannerBlur: String
)
