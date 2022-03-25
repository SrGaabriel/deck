package com.deck.rest.route

import com.deck.common.entity.RawServerBan
import com.deck.common.util.GenericId
import com.deck.rest.RestClient
import com.deck.rest.request.GetServerBansResponse
import com.deck.rest.util.Route
import io.ktor.http.*

public class ServerRoute(client: RestClient): Route(client) {
    public suspend fun getServerBans(serverId: GenericId): List<RawServerBan> = sendRequest<GetServerBansResponse, Unit>(
        endpoint = "/servers/${serverId}/bans",
        method = HttpMethod.Get
    ).serverMemberBans
}