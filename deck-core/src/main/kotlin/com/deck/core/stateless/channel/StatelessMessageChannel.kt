package com.deck.core.stateless.channel

import com.deck.common.util.GenericId
import com.deck.core.builder.DeckMessageBuilder
import com.deck.core.entity.Message
import com.deck.core.entity.channel.MessageChannel
import com.deck.core.stateless.StatelessEntity
import com.deck.core.util.sendMessage

public interface StatelessMessageChannel: StatelessEntity<MessageChannel>, StatelessChannel {
    public val teamId: GenericId?

    public suspend fun sendMessage(builder: DeckMessageBuilder.() -> Unit): Message =
        client.entityDecoder.decodePartialSentMessage(
            id,
            teamId,
            client.rest.channelRoute.sendMessage(id, builder).message
        )

    override suspend fun getState(): MessageChannel {
        return client.entityDelegator.getChannel(id, teamId) as? MessageChannel ?: error("Tried to access a deleted channel's state")
    }
}