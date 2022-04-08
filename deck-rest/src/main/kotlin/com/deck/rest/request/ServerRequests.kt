package com.deck.rest.request

import com.deck.common.entity.RawServerBan
import com.deck.common.entity.RawServerMember
import kotlinx.serialization.Serializable

@Serializable
public data class GetServerMemberResponse(
    val member: RawServerMember
)

@Serializable
public data class GetServerMembersResponse(
    val members: List<RawServerMember>
)

@Serializable
public data class GetServerBansResponse(
    val serverMemberBans: List<RawServerBan>
)