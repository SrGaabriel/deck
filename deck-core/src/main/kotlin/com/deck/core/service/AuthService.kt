package com.deck.core.service

import com.deck.common.util.Authentication
import com.deck.common.util.AuthenticationResult
import com.deck.core.DeckClient
import com.deck.rest.util.authentication

// fun interface because I feel like it
public fun interface AuthService {
    public suspend fun login(authentication: Authentication): AuthenticationResult
}

public class DefaultAuthService(private val client: DeckClient) : AuthService {
    override suspend fun login(authentication: Authentication): AuthenticationResult = authentication(authentication, client.rest.authRoute).also { result ->
        client.gateway.auth = result
        client.rest.restClient.token = result.token
        client.rest.restClient.selfId = result.self.user.id
        client.cache.updateUser(result.self.user.id, client.entityDecoder.decodeSelf(result.self))
    }
}
