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

    /** Account email (not collected nor stored, just used to authenticate in guilded) */
    public var email: String? = null
    /** Account password (not collected nor stored, just used to authenticate in guilded) */
    public var password: String? = null

    /** When enabled starts to send gateway events JSON and other debugs */
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