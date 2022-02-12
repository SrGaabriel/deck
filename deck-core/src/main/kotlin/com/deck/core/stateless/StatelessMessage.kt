package com.deck.core.stateless

import com.deck.common.util.IntGenericId
import com.deck.core.builder.DeckMessageBuilder
import com.deck.core.entity.Message
import com.deck.core.stateless.channel.StatelessMessageChannel
import java.util.*

public interface StatelessMessage: StatelessEntity<Message> {
    public val id: UUID
    public val channel: StatelessMessageChannel

    public suspend fun sendReply(builder: DeckMessageBuilder.() -> Unit): Message = channel.sendMessage {
        this.let(builder)
        this.repliesTo = this@StatelessMessage.id
    }

    public suspend fun addReaction(reactionId: IntGenericId): Unit =
        client.rest.channelRoute.addReaction(channelId = channel.id, id, reactionId)

    public suspend fun removeReaction(reactionId: IntGenericId): Unit =
        client.rest.channelRoute.deleteOwnReaction(channelId = channel.id, id, reactionId)

    public suspend fun pin(): Unit =
        client.rest.channelRoute.pinMessage(channelId = channel.id, id)

    public suspend fun unpin(): Unit =
        client.rest.channelRoute.unpinMessage(channelId = channel.id, id)

    public suspend fun delete(): Unit =
        client.rest.channelRoute.deleteMessage(channelId = channel.id, id)

    override suspend fun getState(): Message {
        TODO("Not yet implemented")
    }
}