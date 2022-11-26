package io.github.deck.core.stateless.channel

import io.github.deck.common.util.GenericId
import io.github.deck.core.entity.channel.Channel
import io.github.deck.core.entity.impl.DeckServerChannel
import io.github.deck.core.stateless.StatelessEntity
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.util.BlankStatelessServer
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