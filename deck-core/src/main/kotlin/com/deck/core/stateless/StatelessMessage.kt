package com.deck.core.stateless

import com.deck.common.util.IntGenericId
import com.deck.core.builder.DeckMessageBuilder
import com.deck.core.entity.Message
import com.deck.core.stateless.channel.StatelessMessageChannel
import java.util.*

public interface StatelessMessage: StatelessEntity<Message> {
    public val id: UUID
    public val channel: StatelessMessageChannel

    /**
     * Sends a message replying this one.
     *
     * @param builder reply builder
     *
     * @return created message
     */
    public suspend fun sendReply(builder: DeckMessageBuilder.() -> Unit): Message = channel.sendMessage {
        this.let(builder)
        this.repliesTo = this@StatelessMessage.id
    }

    /**
     * Adds a reaction to this message.
     *
     * @param reactionId reaction id
     */
    public suspend fun addReaction(reactionId: IntGenericId): Unit =
        client.rest.channelRoute.addReaction(channelId = channel.id, id, reactionId)

    /**
     * Removes the reaction from this message.
     *
     * @param reactionId reaction id
     */
    public suspend fun removeReaction(reactionId: IntGenericId): Unit =
        client.rest.channelRoute.deleteOwnReaction(channelId = channel.id, id, reactionId)

    /**
     * Pins this message in the channel
     */
    public suspend fun pin(): Unit =
        client.rest.channelRoute.pinMessage(channelId = channel.id, id)

    /**
     * Unpins this message from the channel
     */
    public suspend fun unpin(): Unit =
        client.rest.channelRoute.unpinMessage(channelId = channel.id, id)

    /**
     * Deletes this message from the channel
     */
    public suspend fun delete(): Unit =
        client.rest.channelRoute.deleteMessage(channelId = channel.id, id)

    override suspend fun getState(): Message {
        TODO("Not yet implemented")
    }
}