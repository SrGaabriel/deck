package com.deck.rest.route

import com.deck.common.entity.RawServerBan
import com.deck.common.entity.RawServerMember
import com.deck.common.util.GenericId
import com.deck.rest.RestClient
import com.deck.rest.request.GetServerBansResponse
import com.deck.rest.request.GetServerMemberResponse
import com.deck.rest.request.GetServerMembersResponse
import com.deck.rest.util.sendRequest
import io.ktor.http.*

public class ServerRoute(private val client: RestClient) {
    public suspend fun getServerMember(
        userId: GenericId,
        serverId: GenericId
    ): RawServerMember = client.sendRequest<GetServerMemberResponse, Unit>(
        endpoint = "/servers/${serverId}/members/${userId}",
        method = HttpMethod.Get
    ).member

    public suspend fun getServerMembers(serverId: GenericId): List<RawServerMember> = client.sendRequest<GetServerMembersResponse, Unit>(
        endpoint = "/servers/${serverId}/members/",
        method = HttpMethod.Get
    ).members

    public suspend fun getServerBans(serverId: GenericId): List<RawServerBan> = client.sendRequest<GetServerBansResponse, Unit>(
        endpoint = "/servers/${serverId}/bans",
        method = HttpMethod.Get
    ).serverMemberBans
}