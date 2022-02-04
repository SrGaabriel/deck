package com.deck.core.util

import com.deck.common.util.Authentication
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

    public var debugMode: Boolean by gatewayModule::logPayloadsJson

    public fun build(): DeckClient {
        require(email != null && password != null) {
            "Tried to build a client without providing neither an email nor a password."
        }
        return DeckClient(Authentication(email!!, password!!, true), restModule, gatewayModule)
    }
}

public inline fun client(builder: ClientBuilder.() -> Unit): DeckClient =
    ClientBuilder().apply(builder).build()