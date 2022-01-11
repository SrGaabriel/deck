package com.deck.gateway.entity

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.OptionalProperty
import kotlinx.serialization.Serializable

@Serializable
data class RawTeamMemberUserInfo(
    val nickname: OptionalProperty<String?> = OptionalProperty.NotPresent,
)

@Serializable
data class RawTeamMemberRoleId(
    val userId: GenericId,
    val roleIds: List<IntGenericId>
)