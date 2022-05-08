package io.github.deck.core.event.documentation

import io.github.deck.common.util.GenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.Documentation
import io.github.deck.core.entity.impl.DeckDocumentation
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.gateway.event.type.GatewayDocumentationDeletedEvent

public data class DocumentationDeleteEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    val serverId: GenericId,
    val documentation: Documentation
): DeckEvent {
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)
}

public val EventService.documentationDeleteEvent: EventMapper<GatewayDocumentationDeletedEvent, DocumentationDeleteEvent>
    get() = mapper { client, event ->
        DocumentationDeleteEvent(
            client = client,
            gatewayId = event.gatewayId,
            serverId = event.serverId,
            documentation = DeckDocumentation.from(client, event.documentation)
    )
}