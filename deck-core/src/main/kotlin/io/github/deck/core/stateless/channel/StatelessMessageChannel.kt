package io.github.deck.core.stateless.channel

import io.github.deck.core.entity.Message
import io.github.deck.core.entity.impl.DeckMessage
import io.github.deck.core.stateless.StatelessEntity
import io.github.deck.rest.builder.SendMessageRequestBuilder
import io.github.deck.rest.builder.UpdateMessageRequestBuilder
import kotlinx.datetime.Instant
import java.util.*

public interface StatelessMessageChannel: StatelessEntity, StatelessChannel {
    /**
     * Creates a new message within this message channel
     *
     * @param builder message builder
     * @return created message
     */
    public suspend fun sendMessage(builder: SendMessageRequestBuilder.() -> Unit): Message =
        DeckMessage.from(client, client.rest.channel.sendMessage(id, builder))

    /**
     * Retrieves a message associated with the provided [messageId] within this message channel
     *
     * @param messageId message id
     * @return found message
     */
    public suspend fun getMessage(messageId: UUID): Message =
        DeckMessage.from(client, client.rest.channel.getMessage(id, messageId))

    /**
     * Retrieves a number of [limit] messages sent in this channel
     *
     * @param before filters messages sent after the specified instant, null by default
     * @param after filters messages sent before the specified instant, null by default
     * @param limit specifies how many messages to be retrieved **(min 1 max 100)**
     * @param includePrivate whether to include private messages between all users
     */
    public suspend fun getMessages(
        before: Instant? = null,
        after: Instant? = null,
        limit: Int = 50,
        includePrivate: Boolean = false
    ): List<Message> =
        client.rest.channel.getChannelMessages(channelId = id, before = before, after = after, limit = limit, includePrivate = includePrivate).map { DeckMessage.from(client, it) }

    /**
     * Updates **(NOT PATCHES)** the message associated with the specified [messageId].
     *
     * @param messageId message id
     * @param builder update builder
     *
     * @return updated message
     */
    public suspend fun updateMessage(messageId: UUID, builder: UpdateMessageRequestBuilder.() -> Unit): Message =
        DeckMessage.from(client, client.rest.channel.updateMessage(id, messageId, builder))

    /**
     * Deletes the message associated with the provided [messageId]
     *
     * @param messageId message id
     */
    public suspend fun deleteMessage(messageId: UUID): Unit =
        client.rest.channel.deleteMessage(id, messageId)
}