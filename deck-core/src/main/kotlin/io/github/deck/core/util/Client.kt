package io.github.deck.core.util

import io.github.deck.core.DeckClient
import io.github.deck.gateway.GatewayOrchestrator
import io.github.deck.rest.RestClient

public class ClientBuilder(token: String) {
    public var rest: RestClient = RestClient(token)
    public var gateway: GatewayOrchestrator = GatewayOrchestrator(token)

    public fun build(): DeckClient {
        return DeckClient(rest, gateway)
    }
}