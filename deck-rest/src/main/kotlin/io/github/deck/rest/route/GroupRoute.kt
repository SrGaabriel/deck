package io.github.deck.rest.route

import io.github.deck.common.util.GenericId
import io.github.deck.rest.RestClient
import io.github.deck.rest.util.sendRequest
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