package com.deck.rest.request

import com.deck.common.entity.*
import com.deck.common.util.GenericId
import com.deck.common.util.OptionalProperty
import com.deck.common.util.RawUserPresenceType
import kotlinx.serialization.Serializable

@Serializable
data class SelfUserResponse(
    val updateMessage: String?,
    val user: RawUser,
    val teams: List<RawTeam>,
    val customReactions: List<RawCustomReaction>,
    val customEmojis: OptionalProperty<List<GenericId>> = OptionalProperty.NotPresent,
    val landingUrl: Boolean,
    val friends: List<RawFriend>
)

@Serializable
data class UserResponse(
    val user: RawUser
)

@Serializable
data class ModifySelfUserRequest(
    var name: String?,
    var avatar: String?,
    var subdomain: String?,
    var aboutInfo: RawUserAboutInfo?
)

@Serializable
data class UserDMResponse(
    val channels: List<RawPrivateChannel>
)

@Serializable
data class CreateDMChannelRequest(
    val users: List<GenericId>
)

@Serializable
data class SetSelfPresenceRequest(
    val status: RawUserPresenceStatus
)

@Serializable
data class SetUserTransientStatusRequest(
    val id: Int,
    val gameId: Int?,
    val type: RawUserPresenceType
)

@Serializable
data class SelfReferralStatisticsResponse(
    val referrals: Int,
    val stonks: Int,
    val requiredForNextStonk: Int
)
