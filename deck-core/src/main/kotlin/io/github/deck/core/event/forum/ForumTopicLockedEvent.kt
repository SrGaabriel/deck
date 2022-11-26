package io.github.deck.core.event.forum

import io.github.deck.core.DeckClient
import io.github.deck.core.entity.ForumTopic
import io.github.deck.core.entity.impl.DeckForumTopic
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.channel.StatelessForumChannel
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayForumTopicLockedEvent

/**
 * Called when a new [ForumTopic] is locked
 */
public data class ForumTopicLockedEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val forumTopic: ForumTopic
): DeckEvent {
    inline val channel: StatelessForumChannel get() = forumTopic.channel
}

internal val EventService.forumTopicLockedEvent: EventMapper<GatewayForumTopicLockedEvent, ForumTopicLockedEvent>
    get() = mapper { client, event ->
        ForumTopicLockedEvent(
            client = client,
            barebones = event,
            forumTopic = DeckForumTopic.from(client, event.forumTopic)
        )
    }