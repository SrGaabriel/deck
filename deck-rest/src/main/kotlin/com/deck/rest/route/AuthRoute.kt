package com.deck.rest.route

import com.deck.common.util.Authentication
import com.deck.rest.RestClient
import com.deck.rest.util.Route
import io.ktor.client.statement.*
import io.ktor.http.*

class AuthRoute(client: RestClient): Route(client) {
    suspend fun login(authentication: Authentication) = sendRequest<HttpResponse, Authentication>(
        endpoint = "/login",
        method = HttpMethod.Post,
        body = authentication,
        authenticated = false
    )
}