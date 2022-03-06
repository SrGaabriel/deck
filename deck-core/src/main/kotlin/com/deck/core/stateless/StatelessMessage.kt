package com.deck.core.stateless

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.builder.DeckMessageBuilder
import com.deck.core.entity.Message
import com.deck.core.stateless.channel.StatelessMessageChannel
import com.deck.core.util.BlankStatelessMessageChannel
import com.deck.core.util.BlankStatelessTeam
import com.deck.core.util.sendMessage
import java.util.*

public interface StatelessMessage: StatelessEntity<Message> {
    public val id: UUID
    public val channelId: UUID
    public val teamId: GenericId?

    public val channel: StatelessMessageChannel get() = BlankStatelessMessageChannel(client, channelId, teamId)
    public val team: StatelessTeam? get() = teamId?.let { BlankStatelessTeam(client, it) }

    /**
     * Sends a message replying this one.
     *
     * @param builder reply builder
     *
     * @return created message
     */
    public suspend fun sendReply(builder: DeckMessageBuilder.() -> Unit): Message =
        client.entityDecoder.decodePartialSentMessage(channelId, teamId, client.rest.channelRoute.sendMessage(channelId, builder).message)

    /**
     * Adds a reaction to this message.
     *
     * @param reactionId reaction id
     */
    public suspend fun addReaction(reactionId: IntGenericId): Unit =
        client.rest.channelRoute.addReaction(channelId = channelId, id, reactionId)

    /**
     * Removes the reaction from this message.
     *
     * @param reactionId reaction id
     */
    public suspend fun removeReaction(reactionId: IntGenericId): Unit =
        client.rest.channelRoute.deleteOwnReaction(channelId = channelId, id, reactionId)

    /**
     * Pins this message in the channel
     */
    public suspend fun pin(): Unit =
        client.rest.channelRoute.pinMessage(channelId = channelId, id)

    /**
     * Unpins this message from the channel
     */
    public suspend fun unpin(): Unit =
        client.rest.channelRoute.unpinMessage(channelId = channelId, id)

    /**
     * Deletes this message from the channel
     */
    public suspend fun delete(): Unit =
        client.rest.channelRoute.deleteMessage(channelId = channelId, id)

    override suspend fun getState(): Message {
        TODO("Not yet implemented")
    }
}