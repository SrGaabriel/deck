package com.guildedkt.request

import com.guildedkt.entity.RawFriend
import com.guildedkt.entity.RawPrivateChannel
import com.guildedkt.entity.RawTeam
import com.guildedkt.entity.RawUser
import com.guildedkt.entity.RawUserAboutInfo
import com.guildedkt.util.GenericId
import com.guildedkt.util.OptionalProperty
import kotlinx.serialization.Serializable

@Serializable
data class SelfUserResponse(
    val updateMessage: String?,
    val user: RawUser,
    val teams: List<RawTeam>,
    val customReactions: List<GenericId>,
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
data class SetUserTransientStatusRequest(
    val id: Int,
    val gameId: Int?,
    val type: String
)

@Serializable
data class SelfReferralStatisticsResponse(
    val referrals: Int,
    val stonks: Int,
    val requiredForNextStonk: Int
)