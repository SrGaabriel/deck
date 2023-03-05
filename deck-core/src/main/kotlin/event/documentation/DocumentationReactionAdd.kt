package io.github.srgaabriel.deck.core.event.documentation

import io.github.srgaabriel.deck.common.util.Emote
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.common.util.getValue
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.Documentation
import io.github.srgaabriel.deck.core.event.DeckEvent
import io.github.srgaabriel.deck.core.event.EventMapper
import io.github.srgaabriel.deck.core.event.EventService
import io.github.srgaabriel.deck.core.event.mapper
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.StatelessUser
import io.github.srgaabriel.deck.core.stateless.channel.StatelessMessageChannel
import io.github.srgaabriel.deck.core.stateless.channel.content.StatelessDocumentation
import io.github.srgaabriel.deck.core.util.BlankStatelessDocumentation
import io.github.srgaabriel.deck.core.util.BlankStatelessMessageChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessServer
import io.github.srgaabriel.deck.core.util.BlankStatelessUser
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayDocumentationReactionCreatedEvent
import java.util.*

/**
 * Called when someone reacts to a [Documentation]
 * (not only when a new reaction emote is added to a doc)
 */
public data class DocumentationReactionAddEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val serverId: GenericId,
    val channelId: UUID,
    val documentationId: IntGenericId,
    val userId: GenericId,
    val emote: Emote
): DeckEvent {
    val server: StatelessServer? by lazy { BlankStatelessServer(client, serverId) }
    val channel: StatelessMessageChannel by lazy { BlankStatelessMessageChannel(client, channelId, serverId) }
    val documentation: StatelessDocumentation by lazy { BlankStatelessDocumentation(client, documentationId, channelId, serverId) }
    val user: StatelessUser by lazy { BlankStatelessUser(client, userId) }
}

internal val EventService.documentationReactionAdd: EventMapper<GatewayDocumentationReactionCreatedEvent, DocumentationReactionAddEvent> get() = mapper { client, event ->
    DocumentationReactionAddEvent(
        client = client,
        barebones = event,
        serverId = event.serverId.getValue(),
        channelId = event.reaction.channelId,
        documentationId = event.reaction.docId,
        userId = event.reaction.createdBy,
        emote = Emote.from(event.reaction.emote)
    )
}