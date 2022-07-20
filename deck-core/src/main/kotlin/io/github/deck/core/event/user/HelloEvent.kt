package io.github.deck.core.event.user

import io.github.deck.core.DeckClient
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.gateway.event.Payload
import io.github.deck.gateway.event.type.GatewayHelloEvent

public data class HelloEvent(
    override val client: DeckClient,
    override val payload: Payload,
    public val heartbeatInterval: Long,
    public val lastMessageId: String
) : DeckEvent

public val EventService.helloEvent: EventMapper<GatewayHelloEvent, HelloEvent> get() = mapper { client, event ->
    HelloEvent(
        client = client,
        payload = event.payload,
        heartbeatInterval = event.heartbeatIntervalMs,
        lastMessageId = event.lastMessageId
    )
}