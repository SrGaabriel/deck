package io.github.srgaabriel.deck.core.event.forum

import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.ForumTopicComment
import io.github.srgaabriel.deck.core.entity.impl.DeckForumTopicComment
import io.github.srgaabriel.deck.core.event.DeckEvent
import io.github.srgaabriel.deck.core.event.EventMapper
import io.github.srgaabriel.deck.core.event.EventService
import io.github.srgaabriel.deck.core.event.mapper
import io.github.srgaabriel.deck.core.stateless.StatelessForumTopic
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.channel.StatelessForumChannel
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayForumTopicCommentCreatedEvent

/**
 * Called when a new [ForumTopicComment] is posted
 */
public data class ForumTopicCommentCreateEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val forumTopicComment: ForumTopicComment
): DeckEvent {
    public val server: StatelessServer by lazy { forumTopicComment.server }
    public val channel: StatelessForumChannel by lazy { forumTopicComment.channel }
    public val topic: StatelessForumTopic by lazy { forumTopicComment.forumTopic }
}

internal val EventService.forumTopicCommentCreate: EventMapper<GatewayForumTopicCommentCreatedEvent, ForumTopicCommentCreateEvent>
    get() = mapper { client, event ->
        ForumTopicCommentCreateEvent(
            client = client,
            barebones = event,
            forumTopicComment = DeckForumTopicComment.from(client, event.serverId, event.forumTopicComment)
        )
    }