package io.github.srgaabriel.deck.core.stateless.channel.content

import io.github.srgaabriel.deck.common.entity.ServerChannelType
import io.github.srgaabriel.deck.common.util.DeckDelicateApi
import io.github.srgaabriel.deck.common.util.Emote
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.core.stateless.StatelessEntity
import io.github.srgaabriel.deck.core.util.ReactionHolder
import java.util.*

public interface StatelessChannelContent: StatelessEntity, ReactionHolder {
    public val id: IntGenericId
    public val channelId: UUID
    public val channelType: ServerChannelType

    @DeckDelicateApi
    override suspend fun addReaction(reactionId: IntGenericId): Unit =
        client.rest.channel.addReactionToContent(
            channelId,
            ServerChannelType.Calendar,
            id,
            reactionId
        )

    @DeckDelicateApi
    override suspend fun removeReaction(reactionId: IntGenericId): Unit =
        client.rest.channel.removeReactionFromContent(
            channelId,
            ServerChannelType.Calendar,
            id,
            reactionId
        )

    @DeckDelicateApi
    public suspend fun addReactionToComment(commentId: IntGenericId, reactionId: IntGenericId): Unit =
        client.rest.channel.addReactionToComment(
            channelId,
            ServerChannelType.Calendar,
            id,
            commentId,
            reactionId
        )

    @DeckDelicateApi
    public suspend fun removeReactionFromComment(commentId: IntGenericId, reactionId: IntGenericId): Unit =
        client.rest.channel.removeReactionFromComment(
            channelId,
            ServerChannelType.Calendar,
            id,
            commentId,
            reactionId
        )

    @DeckDelicateApi
    public suspend fun addReactionToComment(commentId: IntGenericId, emote: Emote): Unit =
        addReactionToComment(commentId, emote.id)

    @DeckDelicateApi
    public suspend fun removeReactionFromComment(commentId: IntGenericId, emote: Emote): Unit =
        addReactionToComment(commentId, emote.id)
}