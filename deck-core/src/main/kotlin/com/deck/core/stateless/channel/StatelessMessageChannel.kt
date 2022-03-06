package com.deck.core.stateless.channel

import com.deck.common.util.GenericId
import com.deck.core.entity.Message
import com.deck.core.stateless.StatelessEntity
import com.deck.core.stateless.StatelessServer
import com.deck.core.util.BlankStatelessServer
import com.deck.rest.builder.SendMessageRequestBuilder
import java.util.*

public interface StatelessMessageChannel: StatelessEntity {
    public val id: UUID
    public val serverId: GenericId?

    public val server: StatelessServer? get() = serverId?.let { BlankStatelessServer(client, it) }

    public suspend fun sendMessage(builder: SendMessageRequestBuilder.() -> Unit): Message =
        client.entityDecoder.decodeMessage(
            client.rest.channelRoute.sendMessage(id, builder)
        )

    public suspend fun getMessage(messageId: UUID): Message? =
        client.rest.channelRoute.getMessage(id, messageId)?.let {
            client.entityDecoder.decodeMessage(
                it
            )
        }

    public suspend fun getMessages(includePrivate: Boolean = false): List<Message> =
        client.rest.channelRoute.getChannelMessages(id, includePrivate).map(client.entityDecoder::decodeMessage)

    public suspend fun updateMessage(messageId: UUID, content: String): Message =
        client.entityDecoder.decodeMessage(
            client.rest.channelRoute.updateMessage(id, messageId, content)
        )

    public suspend fun deleteMessage(messageId: UUID): Unit =
        client.rest.channelRoute.deleteMessage(id, messageId)


}