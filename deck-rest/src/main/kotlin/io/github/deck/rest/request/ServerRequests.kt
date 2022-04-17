package io.github.deck.rest.request

import io.github.deck.common.entity.RawServerBan
import io.github.deck.common.entity.RawServerMember
import io.github.deck.common.entity.RawServerMemberSummary
import kotlinx.serialization.Serializable

@Serializable
public data class GetServerMemberResponse(
    val member: RawServerMember
)

@Serializable
public data class GetServerMembersResponse(
    val members: List<RawServerMemberSummary>
)

@Serializable
public data class GetServerBansResponse(
    val serverMemberBans: List<RawServerBan>
)