package com.deck.rest.route

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.SocialLinkType
import com.deck.rest.RestClient
import com.deck.rest.request.GetMemberRolesResponse
import com.deck.rest.request.MemberAwardXpRequest
import com.deck.rest.request.UpdateMemberNicknameRequest
import com.deck.rest.util.Route
import io.ktor.http.*
import kotlinx.serialization.json.JsonObject

public class MemberRoute(client: RestClient): Route(client) {
    public suspend fun awardXpToMember(
        userId: GenericId,
        serverId: GenericId,
        amount: Int
    ): Int = sendRequest<MemberAwardXpRequest, MemberAwardXpRequest>(
        endpoint = "/servers/$serverId/members/$userId/xp",
        method = HttpMethod.Post,
        body = MemberAwardXpRequest(amount)
    ).amount

    public suspend fun addRole(
        userId: GenericId,
        serverId: GenericId,
        roleId: IntGenericId
    ): Unit = sendRequest<Unit, Unit>(
        endpoint = "/servers/$serverId/members/$userId/roles/$roleId",
        method = HttpMethod.Put
    )

    public suspend fun removeRole(
        userId: GenericId,
        serverId: GenericId,
        roleId: IntGenericId
    ): Unit = sendRequest<Unit, Unit>(
        endpoint = "/servers/$serverId/members/$userId/roles/$roleId",
        method = HttpMethod.Delete
    )

    public suspend fun updateMemberNickname(
        userId: GenericId,
        serverId: GenericId,
        nickname: String
    ): String = sendRequest<UpdateMemberNicknameRequest, UpdateMemberNicknameRequest>(
        endpoint = "/servers/$serverId/members/$userId/nickname",
        method = HttpMethod.Put,
        body = UpdateMemberNicknameRequest(nickname)
    ).nickname

    public suspend fun removeMemberNickname(
        userId: GenericId,
        serverId: GenericId
    ): Unit = sendRequest<Unit, Unit>(
        endpoint = "/servers/$serverId/members/$userId/nickname",
        method = HttpMethod.Delete
    )

    public suspend fun getMemberRoles(
        userId: GenericId,
        serverId: GenericId
    ): List<IntGenericId> = sendRequest<GetMemberRolesResponse, Unit>(
        endpoint = "/servers/$serverId/members/$userId/roles",
        method = HttpMethod.Get
    ).roleIds

    public fun getMemberSocialLinks(
        userId: GenericId,
        serverId: GenericId,
        type: SocialLinkType
    ): JsonObject = TODO("Not yet implemented")
}