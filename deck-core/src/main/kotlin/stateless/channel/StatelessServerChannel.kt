package io.github.srgaabriel.deck.core.stateless.channel

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.core.entity.channel.ServerChannel
import io.github.srgaabriel.deck.core.entity.impl.DeckServerChannel
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.util.BlankStatelessServer

public interface StatelessServerChannel: StatelessChannel {
    override val serverId: GenericId

    override val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    /**
     * Deletes this channel
     */
    public suspend fun delete(): Unit =
        client.rest.channel.deleteChannel(id)

    /**
     * Retrieves a [ServerChannel] that the bot has access to
     *
     * @return the [ServerChannel] matching this channel's id
     */
    override suspend fun getChannel(): ServerChannel =
        DeckServerChannel.from(client, client.rest.channel.getChannel(id))
}