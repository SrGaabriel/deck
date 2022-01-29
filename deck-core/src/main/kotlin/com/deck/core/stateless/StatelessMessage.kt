package com.deck.core.stateless

import com.deck.common.util.DeckObsoleteApi
import com.deck.common.util.IntGenericId
import com.deck.core.entity.Message
import com.deck.core.stateless.channel.StatelessMessageChannel
import com.deck.core.util.ReactionHolder
import com.deck.rest.builder.SendMessageRequestBuilder
import java.util.*

public interface StatelessMessage: StatelessEntity, ReactionHolder {
    public val id: UUID
    public val channel: StatelessMessageChannel

    public suspend fun sendReply(builder: SendMessageRequestBuilder.() -> Unit): Message = channel.sendMessage {
        builder(this)
        addReplyTarget(this@StatelessMessage.id)
    }

    override suspend fun addReaction(reactionId: IntGenericId): Unit =
        client.rest.channelRoute.addReactionToContent(channel.id, id, reactionId)

    @DeckObsoleteApi
    override suspend fun removeReaction(reactionId: IntGenericId): Unit =
        client.rest.channelRoute.removeReactionFromContent(channel.id, id, reactionId)

    public suspend fun update(content: String): Message =
        channel.updateMessage(id, content)

    public suspend fun delete(): Unit =
        channel.deleteMessage(id)
}