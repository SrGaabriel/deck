package com.deck.core.stateless.channel

import com.deck.core.builder.DeckMessageBuilder
import com.deck.core.entity.Message
import com.deck.core.entity.channel.MessageChannel
import com.deck.core.stateless.StatelessEntity
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.standard.StandardStatelessChannel
import com.deck.core.util.sendMessage

public interface StatelessMessageChannel: StatelessEntity<MessageChannel>, StandardStatelessChannel {
    public val team: StatelessTeam?

    public suspend fun sendMessage(builder: DeckMessageBuilder.() -> Unit): Message =
        client.entityDecoder.decodePartialSentMessage(
            id,
            team?.id,
            client.rest.channelRoute.sendMessage(id, builder).message
        )

    override suspend fun getState(): MessageChannel {
        return client.entityDelegator.getChannel(id, team?.id) as? MessageChannel ?: error("Tried to access a deleted channel's state")
    }
}