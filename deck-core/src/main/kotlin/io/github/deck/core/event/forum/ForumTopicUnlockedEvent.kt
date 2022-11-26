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
import io.github.deck.gateway.event.type.GatewayForumTopicUnlockedEvent

/**
 * Called when a new [ForumTopic] is unlocked
 */
public data class ForumTopicUnlockedEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val forumTopic: ForumTopic
): DeckEvent {
    inline val server: StatelessServer get() = forumTopic.server
    inline val channel: StatelessForumChannel get() = forumTopic.channel
}

internal val EventService.forumTopicUnlockedEvent: EventMapper<GatewayForumTopicUnlockedEvent, ForumTopicUnlockedEvent>
    get() = mapper { client, event ->
        ForumTopicUnlockedEvent(
            client = client,
            barebones = event,
            forumTopic = DeckForumTopic.from(client, event.forumTopic)
        )
    }