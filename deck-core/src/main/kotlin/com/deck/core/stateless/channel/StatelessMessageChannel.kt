package com.deck.core.stateless.channel

import com.deck.common.util.GenericId
import com.deck.core.entity.Message
import com.deck.core.entity.impl.DeckMessage
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
        DeckMessage.strategize(client, client.rest.channel.sendMessage(id, builder))

    public suspend fun getMessage(messageId: UUID): Message? =
        DeckMessage.strategize(client, client.rest.channel.getMessage(id, messageId))

    public suspend fun getMessages(includePrivate: Boolean = false): List<Message> =
        client.rest.channel.getChannelMessages(id, includePrivate).map { DeckMessage.strategize(client, it) }

    public suspend fun updateMessage(messageId: UUID, content: String): Message =
        DeckMessage.strategize(client, client.rest.channel.updateMessage(id, messageId, content))

    public suspend fun deleteMessage(messageId: UUID): Unit =
        client.rest.channel.deleteMessage(id, messageId)


}