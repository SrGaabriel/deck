package io.github.deck.rest.request

import io.github.deck.common.entity.RawServerBan
import io.github.deck.common.entity.RawUserSocialLink
import kotlinx.serialization.Serializable

@Serializable
public data class UpdateMemberNicknameRequest(
    val nickname: String
)

@Serializable
public data class GetMemberRolesResponse(
    val roleIds: List<Int>
)

@Serializable
public data class GetMemberSocialLinkResponse(
    val socialLink: RawUserSocialLink
)

@Serializable
public data class GetServerMemberBanResponse(
    val serverMemberBan: RawServerBan
)

@Serializable
public data class MemberModifyXpAmountDAO constructor(
    val amount: Int
)

@Serializable
public data class MemberModifyXpTotalDAO(
    val total: Int
)