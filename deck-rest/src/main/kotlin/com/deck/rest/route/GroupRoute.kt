package com.deck.rest.route

import com.deck.common.util.GenericId
import com.deck.rest.RestClient
import com.deck.rest.util.sendRequest
import io.ktor.http.*

public class GroupRoute(private val client: RestClient) {
    public suspend fun addMember(
        userId: GenericId,
        groupId: GenericId
    ): Unit = client.sendRequest<Unit, Unit>(
        endpoint = "/groups/$groupId/members/$userId",
        method = HttpMethod.Put
    )

    public suspend fun removeMember(
        userId: GenericId,
        groupId: GenericId
    ): Unit = client.sendRequest<Unit, Unit>(
        endpoint = "/groups/$groupId/members/$userId",
        method = HttpMethod.Delete
    )
}