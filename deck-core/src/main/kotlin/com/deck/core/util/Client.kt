package com.deck.core.util

import com.deck.core.DeckClient
import com.deck.gateway.GatewayOrchestrator
import com.deck.rest.RestClient

public class ClientBuilder(token: String) {
    public var rest: RestClient = RestClient(token)
    public var gateway: GatewayOrchestrator = GatewayOrchestrator(token)

    public fun build(): DeckClient {
        return DeckClient(rest, gateway)
    }
}