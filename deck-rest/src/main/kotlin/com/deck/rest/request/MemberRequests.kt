package com.deck.rest.request

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
public data class MemberAwardXpRequest @OptIn(ExperimentalSerializationApi::class) constructor(
    @JsonNames("amount", "total")
    val amount: Int
)