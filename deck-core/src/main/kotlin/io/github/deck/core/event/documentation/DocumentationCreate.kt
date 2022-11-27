package io.github.deck.core.event.documentation

import io.github.deck.core.DeckClient
import io.github.deck.core.entity.Documentation
import io.github.deck.core.entity.impl.DeckDocumentation
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.stateless.channel.StatelessDocumentationChannel
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayDocumentationCreatedEvent

/**
 * Called when a [Documentation] is created in a server channel
 */
public data class DocumentationCreateEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val documentation: Documentation
): DeckEvent {
    val server: StatelessServer by lazy { documentation.server }
    val channel: StatelessDocumentationChannel by lazy { documentation.channel }
}

internal val EventService.documentationCreate: EventMapper<GatewayDocumentationCreatedEvent, DocumentationCreateEvent>
    get() = mapper { client, event ->
        DocumentationCreateEvent(
            client = client,
            barebones = event,
            documentation = DeckDocumentation.from(client, event.documentation)
        )
}