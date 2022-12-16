package io.github.srgaabriel.deck.core.stateless.channel

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.core.entity.channel.Channel
import io.github.srgaabriel.deck.core.entity.impl.DeckServerChannel
import io.github.srgaabriel.deck.core.stateless.StatelessEntity
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.util.BlankStatelessServer
import java.util.*

public interface StatelessChannel: StatelessEntity {
    public val id: UUID
    public val serverId: GenericId?

    public val server: StatelessServer? get() = serverId?.let { BlankStatelessServer(client, it) }

    /**
     * Retrieves a [Channel] that the bot has access to
     *
     * @return the [Channel] matching this channel's id
     */
    public suspend fun getChannel(): Channel =
        DeckServerChannel.from(client, client.rest.channel.getChannel(id))
}