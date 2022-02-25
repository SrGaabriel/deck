package com.deck.core.stateless.channel

import com.deck.common.util.mapToModel
import com.deck.core.builder.DeckMessageBuilder
import com.deck.core.entity.Message
import com.deck.core.entity.channel.MessageChannel
import com.deck.core.stateless.StatelessEntity
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.generic.GenericStatelessChannel
import com.deck.core.util.sendMessage
import com.deck.gateway.event.GatewayTypingCommand

public interface StatelessMessageChannel: StatelessEntity<MessageChannel>, GenericStatelessChannel {
    override val team: StatelessTeam?

    /**
     * Sends a message to this message channel
     *
     * @param builder message builder
     */
    public suspend fun sendMessage(builder: DeckMessageBuilder.() -> Unit): Message =
        client.entityDecoder.decodePartialSentMessage(
            id,
            team?.id,
            client.rest.channelRoute.sendMessage(id, builder).message
        )

    /**
     * Makes self show as "typing" in this message channel. Lasts for approximately 3 seconds.
     */
    public suspend fun startTyping(): Unit =
        client.gateway.globalGateway.sendCommand(GatewayTypingCommand(id.mapToModel()))

    override suspend fun getState(): MessageChannel {
        return client.entityDelegator.getChannel(id, team?.id) as? MessageChannel ?: error("Tried to access a deleted channel's state")
    }
}