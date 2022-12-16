package io.github.srgaabriel.deck.core.entity.channel

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.channel.StatelessServerChannel

public interface ServerChannel: Channel, StatelessServerChannel {
    override val serverId: GenericId
    override val server: StatelessServer get() = super<StatelessServerChannel>.server

    public val groupId: GenericId
}