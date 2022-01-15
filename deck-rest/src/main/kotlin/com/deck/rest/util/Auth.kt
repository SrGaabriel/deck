package com.deck.rest.util

import com.deck.common.util.Authentication
import com.deck.common.util.AuthenticationBuilder
import com.deck.common.util.AuthenticationResult
import com.deck.common.util.Constants
import com.deck.rest.RestClient
import com.deck.rest.route.AuthRoute
import io.ktor.client.call.*

suspend fun authentication(authentication: Authentication, authenticationRoute: AuthRoute): AuthenticationResult {
    val response = authenticationRoute.login(authentication)
    val cookie = response.headers["Set-Cookie"]
        ?: error("The authorization credentials seem to be wrong or invalid.")
    println(response.headers)
    println(response.receive<String>())
    return AuthenticationResult.fromCookie(cookie)
}

/**
 * Authenticates with provided credentials sending a request to [Constants.GuildedRestApi]/login
 * and parsing the "Set-Cookie" header.
 *
 * @param authentication authentication credentials
 * @param authRoute authentication route
 */
suspend fun RestClient.authenticate(authentication: Authentication, authRoute: AuthRoute = AuthRoute(this)) {
    this.token = authentication(authentication, authRoute).token
}

/**
 * Authenticates with provided credentials sending a request to [Constants.GuildedRestApi]/login
 * and parsing the "Set-Cookie" header.
 *
 * @param builder authentication builder
 * @param authRoute authentication route
 */
suspend fun RestClient.authenticate(authRoute: AuthRoute = AuthRoute(this), builder: AuthenticationBuilder.() -> Unit) =
    authenticate(AuthenticationBuilder().apply(builder).toSerializableAuthentication(), authRoute).let { this }
