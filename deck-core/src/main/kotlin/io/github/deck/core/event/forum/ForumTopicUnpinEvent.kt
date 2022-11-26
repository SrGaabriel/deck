package io.github.deck.core.event.forum

import io.github.deck.core.DeckClient
import io.github.deck.core.entity.ForumTopic
import io.github.deck.core.entity.impl.DeckForumTopic
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.stateless.channel.StatelessForumChannel
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayForumTopicUnpinnedEvent

/**
 * Called when a new [ForumTopic] is unpinned
 */
public data class ForumTopicUnpinEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val forumTopic: ForumTopic
): DeckEvent {
    inline val server: StatelessServer get() = forumTopic.server
    inline val channel: StatelessForumChannel get() = forumTopic.channel
}

internal val EventService.forumTopicUnpinEvent: EventMapper<GatewayForumTopicUnpinnedEvent, ForumTopicUnpinEvent>
    get() = mapper { client, event ->
        ForumTopicUnpinEvent(
            client = client,
            barebones = event,
            forumTopic = DeckForumTopic.from(client, event.forumTopic)
        )
    }