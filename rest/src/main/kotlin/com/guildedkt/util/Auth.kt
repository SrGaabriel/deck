package com.guildedkt.util

import com.guildedkt.RestClient
import com.guildedkt.route.AuthRoute

suspend fun RestClient.authenticate(authentication: Authentication) {
    val statement = AuthRoute(this).login(authentication)
    val cookie = statement.execute().headers.get("Set-Cookie")
        ?: error("The authorization credentials seem to be wrong or invalid.")
    token = Authentication.extractDataFromCookie(cookie, 0)
}

suspend fun RestClient.authenticate(builder: AuthenticationBuilder.() -> Unit) =
    authenticate(AuthenticationBuilder().apply(builder).toSerializableAuthentication()).let { this }