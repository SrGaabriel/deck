package com.deck.core.util

import com.deck.common.util.Authentication
import com.deck.common.util.AuthenticationResult
import com.deck.core.DeckClient
import com.deck.core.module.DefaultGatewayModule
import com.deck.core.module.DefaultRestModule
import com.deck.core.module.GatewayModule
import com.deck.core.module.RestModule

public class ClientBuilder {
    public var restModule: RestModule = DefaultRestModule()
    public var gatewayModule: GatewayModule = DefaultGatewayModule()

    public var email: String? = null
    public var password: String? = null

    public fun build(): DeckClient {
        require(email != null && password != null) {
            "You can't build a deck client without specifying your bot's authentication credentials."
        }
        return DeckClient(Authentication(email!!, password!!), restModule, gatewayModule)
    }
}

public inline fun client(builder: ClientBuilder.() -> Unit): DeckClient =
    ClientBuilder().apply(builder).build()

public fun DeckClient.setAuthentication(authenticationResult: AuthenticationResult) {
    rest.restClient.token = authenticationResult.token
    gateway.auth = authenticationResult
    this.authenticationResults = authenticationResult
}
