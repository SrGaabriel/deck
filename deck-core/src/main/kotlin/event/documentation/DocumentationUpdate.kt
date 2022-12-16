package io.github.srgaabriel.deck.core.event.documentation

import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.Documentation
import io.github.srgaabriel.deck.core.entity.impl.DeckDocumentation
import io.github.srgaabriel.deck.core.event.DeckEvent
import io.github.srgaabriel.deck.core.event.EventMapper
import io.github.srgaabriel.deck.core.event.EventService
import io.github.srgaabriel.deck.core.event.mapper
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.channel.StatelessDocumentationChannel
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayDocumentationUpdatedEvent

/**
 * Called when a [Documentation] is edited in a server channel
 */
public data class DocumentationUpdateEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val documentation: Documentation
): DeckEvent {
    val server: StatelessServer by lazy { documentation.server }
    val channel: StatelessDocumentationChannel by lazy { documentation.channel }
}

internal val EventService.documentationUpdate: EventMapper<GatewayDocumentationUpdatedEvent, DocumentationUpdateEvent>
    get() = mapper { client, event ->
        DocumentationUpdateEvent(
            client = client,
            barebones = event,
            documentation = DeckDocumentation.from(client, event.documentation)
        )
    }