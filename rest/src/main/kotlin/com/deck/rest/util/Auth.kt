package com.deck.rest.util

import com.deck.common.util.Authentication
import com.deck.common.util.AuthenticationBuilder
import com.deck.common.util.Constants
import com.deck.rest.RestClient
import com.deck.rest.route.AuthRoute

/**
 * Authenticates with provided credentials sending a request to [Constants.GuildedApi]/login
 * and parsing the "Set-Cookie" header.
 *
 * @param authentication authentication credentials
 * @param authRoute authentication route
 */
suspend fun RestClient.authenticate(authentication: Authentication, authRoute: AuthRoute = AuthRoute(this)) {
    val statement = authRoute.login(authentication)
    val cookie = statement.execute().headers["Set-Cookie"]
        ?: error("The authorization credentials seem to be wrong or invalid.")
    token = Authentication.extractDataFromCookie(cookie, 0)
}

/**
 * Authenticates with provided credentials sending a request to [Constants.GuildedApi]/login
 * and parsing the "Set-Cookie" header.
 *
 * @param builder authentication builder
 * @param authRoute authentication route
 */
suspend fun RestClient.authenticate(authRoute: AuthRoute = AuthRoute(this), builder: AuthenticationBuilder.() -> Unit) =
    authenticate(AuthenticationBuilder().apply(builder).toSerializableAuthentication(), authRoute).let { this }