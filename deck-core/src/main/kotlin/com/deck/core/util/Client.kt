package com.deck.core.util

import com.deck.common.util.Authentication
import com.deck.common.util.AuthenticationResult
import com.deck.core.DeckClient
import com.deck.core.module.DefaultGatewayModule
import com.deck.core.module.DefaultRestModule
import com.deck.core.module.GatewayModule
import com.deck.core.module.RestModule

class ClientBuilder {
    var restModule: RestModule = DefaultRestModule()
    var gatewayModule: GatewayModule = DefaultGatewayModule()

    var email: String? = null
    var password: String? = null

    fun build(): DeckClient {
        require(email != null && password != null) {
            "You can't build a deck client without specifying your bot's authentication credentials."
        }
        return DeckClient(Authentication(email!!, password!!), restModule, gatewayModule)
    }
}

inline fun client(builder: ClientBuilder.() -> Unit): DeckClient =
    ClientBuilder().apply(builder).build()

fun DeckClient.setAuthentication(authenticationResult: AuthenticationResult) {
    rest.restClient.token = authenticationResult.token
    gateway.auth = authenticationResult
    this.authenticationResults = authenticationResult
}
