package io.github.srgaabriel.deck.core.event.forum

import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.ForumTopic
import io.github.srgaabriel.deck.core.entity.impl.DeckForumTopic
import io.github.srgaabriel.deck.core.event.DeckEvent
import io.github.srgaabriel.deck.core.event.EventMapper
import io.github.srgaabriel.deck.core.event.EventService
import io.github.srgaabriel.deck.core.event.mapper
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.channel.StatelessForumChannel
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayForumTopicUpdatedEvent

/**
 * Called when a new [ForumTopic] is updated
 */
public data class ForumTopicUpdateEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val forumTopic: ForumTopic
): DeckEvent {
    public val server: StatelessServer by lazy { forumTopic.server }
    public val channel: StatelessForumChannel by lazy { forumTopic.channel }
}

internal val EventService.forumTopicUpdate: EventMapper<GatewayForumTopicUpdatedEvent, ForumTopicUpdateEvent>
    get() = mapper { client, event ->
        ForumTopicUpdateEvent(
            client = client,
            barebones = event,
            forumTopic = DeckForumTopic.from(client, event.forumTopic)
        )
    }