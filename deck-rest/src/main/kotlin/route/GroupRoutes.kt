package io.github.srgaabriel.deck.rest.route

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.rest.RestClient
import io.github.srgaabriel.deck.rest.util.sendRequest
import io.ktor.http.*

public class GroupRoutes(private val client: RestClient) {
    public suspend fun addMember(
        userId: GenericId,
        groupId: GenericId
    ): Unit = client.sendRequest(
        endpoint = "/groups/$groupId/members/$userId",
        method = HttpMethod.Put
    )

    public suspend fun removeMember(
        userId: GenericId,
        groupId: GenericId
    ): Unit = client.sendRequest(
        endpoint = "/groups/$groupId/members/$userId",
        method = HttpMethod.Delete
    )
}