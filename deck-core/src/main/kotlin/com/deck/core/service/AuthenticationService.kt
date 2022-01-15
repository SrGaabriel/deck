package com.deck.core.service

import com.deck.common.util.Authentication
import com.deck.common.util.AuthenticationBuilder
import com.deck.common.util.AuthenticationResult
import com.deck.core.module.RestModule
import com.deck.rest.util.authentication

interface AuthenticationService {
    suspend fun login(authentication: Authentication): AuthenticationResult

    suspend fun login(builder: AuthenticationBuilder.() -> Unit): AuthenticationResult
}

class DefaultAuthenticationService(private val restModule: RestModule): AuthenticationService {
    override suspend fun login(authentication: Authentication): AuthenticationResult =
        authentication(authentication, restModule.authRoute)

    override suspend fun login(builder: AuthenticationBuilder.() -> Unit): AuthenticationResult =
        login(AuthenticationBuilder().apply(builder).toSerializableAuthentication())
}
