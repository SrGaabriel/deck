package io.github.deck.core.event.forum

import io.github.deck.common.util.GenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.ForumTopic
import io.github.deck.core.entity.impl.DeckForumTopic
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.stateless.channel.StatelessForumChannel
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayForumTopicUpdatedEvent

/**
 * Called when a new [ForumTopic] is created
 */
public data class ForumTopicUpdateEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val serverId: GenericId,
    val forumTopic: ForumTopic
): DeckEvent {
    val server: StatelessServer get() = BlankStatelessServer(client, serverId)
    val channel: StatelessForumChannel get() = forumTopic.channel
}

internal val EventService.forumTopicUpdateEvent: EventMapper<GatewayForumTopicUpdatedEvent, ForumTopicUpdateEvent>
    get() = mapper { client, event ->
        ForumTopicUpdateEvent(
            client = client,
            barebones = event,
            serverId = event.serverId,
            forumTopic = DeckForumTopic.from(client, event.forumTopic)
        )
    }