package com.deck.rest.util

import com.deck.common.entity.RawSelfUser
import com.deck.common.util.Authentication
import com.deck.common.util.AuthenticationBuilder
import com.deck.common.util.AuthenticationResult
import com.deck.common.util.Constants
import com.deck.rest.RestClient
import com.deck.rest.route.AuthRoute
import io.ktor.client.call.*

public suspend fun authentication(
    authentication: Authentication,
    authenticationRoute: AuthRoute
): AuthenticationResult {
    val response = authenticationRoute.login(authentication)
    val self = response.receive<RawSelfUser>()
    val cookie = response.headers["Set-Cookie"]
        ?: error("The authorization credentials seem to be wrong or invalid.")
    return AuthenticationResult.fromCookie(self, cookie)
}

/**
 * Authenticates with provided credentials sending a request to [Constants.GuildedRestApi]/login
 * and parsing the "Set-Cookie" header.
 *
 * @param authentication authentication credentials
 * @param authRoute authentication route
 */
public suspend fun RestClient.authenticate(authentication: Authentication, authRoute: AuthRoute = AuthRoute(this)): RestClient = apply {
    val results = authentication(authentication, authRoute)
    this.token = results.token
    this._selfId = results.self.user.id
}

/**
 * Authenticates with provided credentials sending a request to [Constants.GuildedRestApi]/login
 * and parsing the "Set-Cookie" header.
 *
 * @param builder authentication builder
 * @param authRoute authentication route
 */
public suspend fun RestClient.authenticate(
    authRoute: AuthRoute = AuthRoute(this),
    builder: AuthenticationBuilder.() -> Unit
): RestClient = apply {
    authenticate(AuthenticationBuilder().apply(builder).toSerializableAuthentication(), authRoute)
}
