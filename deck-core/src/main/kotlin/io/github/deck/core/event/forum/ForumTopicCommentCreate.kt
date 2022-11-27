package io.github.deck.core.event.forum

import io.github.deck.core.DeckClient
import io.github.deck.core.entity.ForumTopicComment
import io.github.deck.core.entity.impl.DeckForumTopicComment
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessForumTopic
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.stateless.channel.StatelessForumChannel
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayForumTopicCommentCreatedEvent

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