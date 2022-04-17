package io.github.deck.core.stateless.channel

import io.github.deck.common.util.GenericId
import io.github.deck.core.entity.Message
import io.github.deck.core.entity.impl.DeckMessage
import io.github.deck.core.stateless.StatelessEntity
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.rest.builder.SendMessageRequestBuilder
import java.util.*

public interface StatelessMessageChannel: StatelessEntity {
    public val id: UUID
    public val serverId: GenericId?

    public val server: StatelessServer? get() = serverId?.let { BlankStatelessServer(client, it) }

    public suspend fun sendMessage(builder: SendMessageRequestBuilder.() -> Unit): Message =
        DeckMessage.from(client, client.rest.channel.sendMessage(id, builder))

    public suspend fun getMessage(messageId: UUID): Message? =
        DeckMessage.from(client, client.rest.channel.getMessage(id, messageId))

    public suspend fun getMessages(includePrivate: Boolean = false): List<Message> =
        client.rest.channel.getChannelMessages(id, includePrivate).map { DeckMessage.from(client, it) }

    public suspend fun updateMessage(messageId: UUID, content: String): Message =
        DeckMessage.from(client, client.rest.channel.updateMessage(id, messageId, content))

    public suspend fun deleteMessage(messageId: UUID): Unit =
        client.rest.channel.deleteMessage(id, messageId)
}