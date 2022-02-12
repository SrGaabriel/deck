package com.deck.gateway.entity

import com.deck.common.entity.RawUserStatus
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.OptionalProperty
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class RawTeamMemberUserInfo(
    val name: OptionalProperty<String> = OptionalProperty.NotPresent,
    val nickname: OptionalProperty<String?> = OptionalProperty.NotPresent,
    @SerialName("bio") val biography: OptionalProperty<String> = OptionalProperty.NotPresent,
    val tagline: OptionalProperty<String> = OptionalProperty.NotPresent,
    val profilePicture: OptionalProperty<String> = OptionalProperty.NotPresent,
    @SerialName("profileBannerSm") val profileBanner: OptionalProperty<String> = OptionalProperty.NotPresent,
    @SerialName("userStatus") val userStatus: OptionalProperty<RawUserStatus> = OptionalProperty.NotPresent
)

@Serializable
public data class RawTeamMemberRoleId(
    val userId: GenericId,
    val roleIds: List<IntGenericId>
)