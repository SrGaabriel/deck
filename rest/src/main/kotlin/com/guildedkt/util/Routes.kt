package com.guildedkt.util

import com.guildedkt.RestClient
import com.guildedkt.route.AuthRoute
import io.ktor.client.request.*
import io.ktor.client.utils.*
import io.ktor.http.*

abstract class Route(val client: RestClient)

suspend inline fun <reified R, reified B> Route.sendRequest(
    endpoint: String,
    method: HttpMethod,
    body: B? = null,
    authenticated: Boolean = true
) = client.httpClient.request<R>(Constants.GuildedApi + endpoint) {
    this.body = body ?: EmptyContent
    this.method = method

    header(HttpHeaders.ContentType, ContentType.Application.Json)
    if (authenticated)
        header(HttpHeaders.Cookie, "hmac_signed_session=${client.token}")
}

suspend fun RestClient.authenticate(authentication: Authentication) {
    val statement = AuthRoute(this).login(authentication)
    val cookie = statement.execute().headers.get("Set-Cookie")
        ?: error("The authorization credentials seem to be wrong or invalid.")
    token = Authentication.extractDataFromCookie(cookie, 0)
}

suspend fun RestClient.authenticate(builder: AuthenticationBuilder.() -> Unit) =
    authenticate(AuthenticationBuilder().apply(builder).toSerializableAuthentication())
