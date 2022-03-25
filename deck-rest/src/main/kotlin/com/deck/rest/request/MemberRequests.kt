package com.deck.rest.request

import com.deck.common.entity.RawServerBan
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
public data class UpdateMemberNicknameRequest(
    val nickname: String
)

@Serializable
public data class GetMemberRolesResponse(
    val roleIds: List<Int>
)

@Serializable
public data class GetServerMemberBanResponse(
    val serverMemberBan: RawServerBan
)

@Serializable
public data class MemberAwardXpRequest @OptIn(ExperimentalSerializationApi::class) constructor(
    @JsonNames("amount", "total")
    val amount: Int
)