package com.deck.core.service

import com.deck.common.util.Authentication
import com.deck.common.util.AuthenticationBuilder
import com.deck.common.util.AuthenticationResult
import com.deck.rest.route.AuthRoute
import com.deck.rest.util.authentication

public interface AuthService {
    public suspend fun login(authentication: Authentication): AuthenticationResult

    public suspend fun login(builder: AuthenticationBuilder.() -> Unit): AuthenticationResult
}

public class DefaultAuthService(private val route: AuthRoute) : AuthService {
    override suspend fun login(authentication: Authentication): AuthenticationResult =
        authentication(authentication, route)

    override suspend fun login(builder: AuthenticationBuilder.() -> Unit): AuthenticationResult =
        login(AuthenticationBuilder().apply(builder).toSerializableAuthentication())
}
