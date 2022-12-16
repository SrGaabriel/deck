package io.github.srgaabriel.deck.core.event.user

import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.event.DeckEvent
import io.github.srgaabriel.deck.core.event.EventMapper
import io.github.srgaabriel.deck.core.event.EventService
import io.github.srgaabriel.deck.core.event.mapper
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayHelloEvent

/**
 * Called when a gateway connects to guilded
 */
public data class HelloEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    public val heartbeatInterval: Long,
    public val lastMessageId: String
) : DeckEvent

internal val EventService.hello: EventMapper<GatewayHelloEvent, HelloEvent> get() = mapper { client, event ->
    HelloEvent(
        client = client,
        barebones = event,
        heartbeatInterval = event.heartbeatIntervalMs,
        lastMessageId = event.lastMessageId
    )
}