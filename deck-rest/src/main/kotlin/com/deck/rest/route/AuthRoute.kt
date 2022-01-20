package com.deck.rest.route

import com.deck.common.util.Authentication
import com.deck.rest.RestClient
import com.deck.rest.util.Route
import io.ktor.client.statement.*
import io.ktor.http.*

public class AuthRoute(client: RestClient) : Route(client) {
    public suspend fun login(authentication: Authentication): HttpResponse = sendRequest<HttpResponse, Authentication>(
        endpoint = "/login",
        method = HttpMethod.Post,
        body = authentication,
        authenticated = false
    )
}
