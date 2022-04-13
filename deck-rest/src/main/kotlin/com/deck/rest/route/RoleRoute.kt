package com.deck.rest.route

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.rest.RestClient
import com.deck.rest.request.MemberAwardXpRequest
import com.deck.rest.util.sendRequest
import io.ktor.http.*

public class RoleRoute(private val client: RestClient) {
    public suspend fun awardXpToRole(
        roleId: IntGenericId,
        serverId: GenericId,
        amount: Int
    ): Unit = client.sendRequest(
        endpoint = "/servers/$serverId/roles/$roleId/xp",
        method = HttpMethod.Post,
        body = MemberAwardXpRequest(amount)
    )
}