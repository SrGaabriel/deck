package io.github.deck.core.stateless

import io.github.deck.common.util.GenericId
import io.github.deck.common.util.IntGenericId
import io.github.deck.core.entity.Message
import io.github.deck.core.entity.impl.DeckMessage
import io.github.deck.core.stateless.channel.StatelessMessageChannel
import io.github.deck.core.util.BlankStatelessMessageChannel
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.core.util.ReactionHolder
import io.github.deck.rest.builder.SendMessageRequestBuilder
import io.github.deck.rest.builder.UpdateMessageRequestBuilder
import java.util.*

public interface StatelessMessage: StatelessEntity, ReactionHolder {
    public val id: UUID
    public val channelId: UUID
    public val serverId: GenericId?

    public val channel: StatelessMessageChannel get() = BlankStatelessMessageChannel(client, channelId, serverId)
    public val server: StatelessServer? get() = serverId?.let { BlankStatelessServer(client, it) }

    /**
     * Sends another message replying to this one.
     *
     * @param builder reply builder
     * @return the created message
     */
    public suspend fun sendReply(builder: SendMessageRequestBuilder.() -> Unit): Message =
        DeckMessage.from(client, client.rest.channel.sendMessage(channelId) {
            builder(this)
            replyTo(this@StatelessMessage.id)
        })

    /**
     * Adds the specified reaction to this message.
     * For a list of all emoji ids, you can check the extras module.
     *
     * @param reactionId
     */
    override suspend fun addReaction(reactionId: IntGenericId): Unit =
        client.rest.channel.addReactionToContent(channelId, id, reactionId)

    /**
     * Removes the specified reaction to this message.
     * For a list of all emoji ids, you can check the extras module.
     *
     * @param reactionId
     */
    override suspend fun removeReaction(reactionId: IntGenericId): Unit =
        client.rest.channel.removeReactionFromContent(channelId, id, reactionId)

    /**
     * Overwrites this message's content
     *
     * @param builder new content builder
     * @return new message with the new content
     */
    public suspend fun update(builder: UpdateMessageRequestBuilder.() -> Unit): Message =
        DeckMessage.from(client, client.rest.channel.updateMessage(channelId, id, builder))

    /**
     * Deletes this message
     */
    public suspend fun delete(): Unit =
        client.rest.channel.deleteMessage(channelId, id)

    public suspend fun getMessage(): Message =
        DeckMessage.from(client, client.rest.channel.getMessage(channelId, id))
}