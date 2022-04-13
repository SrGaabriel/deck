package com.deck.rest.route

import com.deck.common.entity.RawServerBan
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.SocialLinkType
import com.deck.rest.RestClient
import com.deck.rest.request.GetMemberRolesResponse
import com.deck.rest.request.GetServerMemberBanResponse
import com.deck.rest.request.MemberAwardXpRequest
import com.deck.rest.request.UpdateMemberNicknameRequest
import com.deck.rest.util.sendRequest
import io.ktor.http.*
import kotlinx.serialization.json.JsonObject

public class MemberRoute(private val client: RestClient) {
    public suspend fun awardXpToMember(
        userId: GenericId,
        serverId: GenericId,
        amount: Int
    ): Int = client.sendRequest<MemberAwardXpRequest, MemberAwardXpRequest>(
        endpoint = "/servers/$serverId/members/$userId/xp",
        method = HttpMethod.Post,
        body = MemberAwardXpRequest(amount)
    ).amount

    public suspend fun addRole(
        userId: GenericId,
        serverId: GenericId,
        roleId: IntGenericId
    ): Unit = client.sendRequest<Unit, Unit>(
        endpoint = "/servers/$serverId/members/$userId/roles/$roleId",
        method = HttpMethod.Put
    )

    public suspend fun removeRole(
        userId: GenericId,
        serverId: GenericId,
        roleId: IntGenericId
    ): Unit = client.sendRequest<Unit, Unit>(
        endpoint = "/servers/$serverId/members/$userId/roles/$roleId",
        method = HttpMethod.Delete
    )

    public suspend fun updateMemberNickname(
        userId: GenericId,
        serverId: GenericId,
        nickname: String
    ): String = client.sendRequest<UpdateMemberNicknameRequest, UpdateMemberNicknameRequest>(
        endpoint = "/servers/$serverId/members/$userId/nickname",
        method = HttpMethod.Put,
        body = UpdateMemberNicknameRequest(nickname)
    ).nickname

    public suspend fun removeMemberNickname(
        userId: GenericId,
        serverId: GenericId
    ): Unit = client.sendRequest<Unit, Unit>(
        endpoint = "/servers/$serverId/members/$userId/nickname",
        method = HttpMethod.Delete
    )

    public suspend fun getMemberRoles(
        userId: GenericId,
        serverId: GenericId
    ): List<IntGenericId> = client.sendRequest<GetMemberRolesResponse, Unit>(
        endpoint = "/servers/$serverId/members/$userId/roles",
        method = HttpMethod.Get
    ).roleIds

    public fun getMemberSocialLinks(
        userId: GenericId,
        serverId: GenericId,
        type: SocialLinkType
    ): JsonObject = TODO("Not yet implemented")

    public suspend fun kickMember(
        userId: GenericId,
        serverId: GenericId
    ): Unit = client.sendRequest<Unit, Unit>(
        endpoint = "/servers/$serverId/members/$userId",
        method = HttpMethod.Delete
    )

    public suspend fun banMember(
        userId: GenericId,
        serverId: GenericId
    ): Unit = client.sendRequest<Unit, Unit>(
        endpoint = "/servers/$serverId/bans/$userId",
        method = HttpMethod.Post
    )

    public suspend fun getBan(
        userId: GenericId,
        serverId: GenericId
    ): RawServerBan = client.sendRequest<GetServerMemberBanResponse, Unit>(
        endpoint = "/servers/$serverId/bans/$userId",
        method = HttpMethod.Get
    ).serverMemberBan

    public suspend fun unbanMember(
        userId: GenericId,
        serverId: GenericId
    ): Unit = client.sendRequest<Unit, Unit>(
        endpoint = "/servers/$serverId/bans/$userId",
        method = HttpMethod.Delete
    )
}