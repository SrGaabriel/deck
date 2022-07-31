package io.github.deck.core.stateless.channel

import io.github.deck.common.util.GenericId
import io.github.deck.core.entity.channel.ServerChannel
import io.github.deck.core.entity.impl.DeckServerChannel
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.util.BlankStatelessServer

public interface StatelessServerChannel: StatelessChannel {
    override val serverId: GenericId

    override val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    /**
     * Deletes this channel
     */
    public suspend fun delete(): Unit =
        client.rest.channel.deleteChannel(id)

    /**
     * Retrieves a [ServerChannel] from this [StatelessServerChannel]
     *
     * @return the [ServerChannel] matching this [StatelessServerChannel]'s id
     */
    public suspend fun getChannel(): ServerChannel =
        DeckServerChannel.from(client, client.rest.channel.getChannel(id))
}