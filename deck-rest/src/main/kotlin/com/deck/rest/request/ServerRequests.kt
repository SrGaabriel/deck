package com.deck.rest.request

import com.deck.common.entity.RawServerBan
import com.deck.common.entity.RawServerMember

public data class GetServerMemberResponse(
    val member: RawServerMember
)

public data class GetServerMembersResponse(
    val members: List<RawServerMember>
)

public data class GetServerBansResponse(
    val serverMemberBans: List<RawServerBan>
)