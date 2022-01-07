package com.guildedkt.route

import com.guildedkt.RestClient
import com.guildedkt.util.Authentication
import com.guildedkt.util.Route
import com.guildedkt.util.sendRequest
import io.ktor.client.statement.*
import io.ktor.http.*

class AuthRoute(client: RestClient): Route(client) {
    suspend fun login(authentication: Authentication) = sendRequest<HttpStatement, Authentication>(
        endpoint = "/login",
        method = HttpMethod.Post,
        body = authentication,
        authenticated = false
    )
}