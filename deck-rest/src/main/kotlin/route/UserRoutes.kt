package io.github.srgaabriel.deck.rest.route

import io.github.srgaabriel.deck.common.entity.RawUser
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.rest.RestClient
import io.github.srgaabriel.deck.rest.util.sendRequest
import io.ktor.http.*

public class UserRoutes(private val client: RestClient) {
    public suspend fun getSelf(): RawUser = getUser("@me")

    public suspend fun getUser(userId: GenericId): RawUser = client.sendRequest(
        endpoint = "/users/${userId}",
        method = HttpMethod.Get
    )
}