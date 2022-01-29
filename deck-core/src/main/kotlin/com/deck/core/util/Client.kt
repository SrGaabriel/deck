package com.deck.core.util

import com.deck.core.DeckClient
import com.deck.core.module.DefaultGatewayModule
import com.deck.core.module.DefaultRestModule
import com.deck.core.module.GatewayModule
import com.deck.core.module.RestModule

public class ClientBuilder(private val token: String) {
    public var restModule: RestModule = DefaultRestModule(token)
    public var gatewayModule: GatewayModule = DefaultGatewayModule(token)

    public fun build(): DeckClient {
        return DeckClient(restModule, gatewayModule, token)
    }
}