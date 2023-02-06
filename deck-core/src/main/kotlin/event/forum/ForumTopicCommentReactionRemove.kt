package io.github.srgaabriel.deck.core.event.forum

import io.github.srgaabriel.deck.common.util.Emote
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.common.util.getValue
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.ForumTopic
import io.github.srgaabriel.deck.core.event.DeckEvent
import io.github.srgaabriel.deck.core.event.EventMapper
import io.github.srgaabriel.deck.core.event.EventService
import io.github.srgaabriel.deck.core.event.mapper
import io.github.srgaabriel.deck.core.stateless.StatelessForumTopic
import io.github.srgaabriel.deck.core.stateless.StatelessForumTopicComment
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.StatelessUser
import io.github.srgaabriel.deck.core.stateless.channel.StatelessMessageChannel
import io.github.srgaabriel.deck.core.util.*
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayForumTopicCommentReactionDeletedEvent
import java.util.*

/**
 * Called when someone removes a reaction from a [ForumTopic]
 * (not only when a reaction emote is removed completely from a topic)
 */
public data class ForumTopicCommentReactionRemoveEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val serverId: GenericId,
    val channelId: UUID,
    val forumTopicId: IntGenericId,
    val forumTopicCommentId: IntGenericId,
    val userId: GenericId,
    val emote: Emote
): DeckEvent {
    val server: StatelessServer? by lazy { BlankStatelessServer(client, serverId) }
    val channel: StatelessMessageChannel by lazy { BlankStatelessMessageChannel(client, channelId, serverId) }
    val forumTopic: StatelessForumTopic by lazy { BlankStatelessForumTopic(client, forumTopicId, channelId, serverId) }
    val forumTopicComment: StatelessForumTopicComment by lazy { BlankStatelessForumTopicComment(client, forumTopicId, forumTopicCommentId, channelId, serverId) }
    val user: StatelessUser by lazy { BlankStatelessUser(client, userId) }
}

internal val EventService.forumTopicCommentReactionRemove: EventMapper<GatewayForumTopicCommentReactionDeletedEvent, ForumTopicCommentReactionRemoveEvent> get() = mapper { client, event ->
    ForumTopicCommentReactionRemoveEvent(
        client = client,
        barebones = event,
        serverId = event.serverId.getValue(),
        channelId = event.reaction.channelId,
        forumTopicId = event.reaction.forumTopicId,
        forumTopicCommentId = event.reaction.forumTopicCommentId,
        userId = event.reaction.createdBy,
        emote = Emote.from(event.reaction.emote)
    )
}