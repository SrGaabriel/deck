package io.github.srgaabriel.deck.core.event.documentation

import io.github.srgaabriel.deck.common.util.Emote
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.common.util.getValue
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.DocumentationComment
import io.github.srgaabriel.deck.core.event.DeckEvent
import io.github.srgaabriel.deck.core.event.EventMapper
import io.github.srgaabriel.deck.core.event.EventService
import io.github.srgaabriel.deck.core.event.mapper
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.StatelessUser
import io.github.srgaabriel.deck.core.stateless.channel.StatelessMessageChannel
import io.github.srgaabriel.deck.core.stateless.channel.content.StatelessDocumentation
import io.github.srgaabriel.deck.core.stateless.channel.content.StatelessDocumentationComment
import io.github.srgaabriel.deck.core.util.*
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayDocumentationCommentReactionCreatedEvent
import java.util.*

/**
 * Called when someone reacts to a [DocumentationComment]
 * (not only when a new reaction emote is added to a comment)
 */
public data class DocumentationCommentReactionAddEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val serverId: GenericId,
    val channelId: UUID,
    val documentationId: IntGenericId,
    val documentationCommentId: IntGenericId,
    val userId: GenericId,
    val emote: Emote
): DeckEvent {
    val server: StatelessServer? by lazy { BlankStatelessServer(client, serverId) }
    val channel: StatelessMessageChannel by lazy { BlankStatelessMessageChannel(client, channelId, serverId) }
    val documentation: StatelessDocumentation by lazy { BlankStatelessDocumentation(client, documentationId, channelId, serverId) }
    val documentationComment: StatelessDocumentationComment by lazy { BlankStatelessDocumentationComment(client, documentationId, documentationCommentId, channelId, serverId) }
    val user: StatelessUser by lazy { BlankStatelessUser(client, userId) }
}

internal val EventService.documentationCommentReactionAdd: EventMapper<GatewayDocumentationCommentReactionCreatedEvent, DocumentationCommentReactionAddEvent> get() = mapper { client, event ->
    DocumentationCommentReactionAddEvent(
        client = client,
        barebones = event,
        serverId = event.serverId.getValue(),
        channelId = event.reaction.channelId,
        documentationId = event.reaction.docId,
        documentationCommentId = event.reaction.docCommentId,
        userId = event.reaction.createdBy,
        emote = Emote.from(event.reaction.emote)
    )
}