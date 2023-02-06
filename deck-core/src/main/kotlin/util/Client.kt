package io.github.srgaabriel.deck.core.util

import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.gateway.GatewayOrchestrator
import io.github.srgaabriel.deck.rest.RestClient

/**
 * A builder for a [DeckClient]
 */
public class ClientBuilder(token: String) {
    public var rest: RestClient = RestClient(token)
    public var gateway: GatewayOrchestrator = GatewayOrchestrator(token)

    public var logRequests: Boolean by rest::logRequests
    public var logEventPayloads: Boolean by gateway::logEventPayloads

    public var enableEventReplaying: Boolean by gateway::enableEventReplaying
    public var automaticPrivateRepliesToPrivateMessages: Boolean? = null

    /**
     * Enables request (including responses) and event payloads logging
     */
    public fun debugMode() {
        logRequests = true
        logEventPayloads = true
    }

    public fun build(): DeckClient {
        val client = DeckClient(rest, gateway)
        if (automaticPrivateRepliesToPrivateMessages != null)
            client.privateRepliesToPrivateMessagesByDefault = automaticPrivateRepliesToPrivateMessages!!
        return client
    }
}