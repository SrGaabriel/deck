package io.github.deck.core.entity.channel

import io.github.deck.common.util.GenericId
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.stateless.channel.StatelessServerChannel

public interface ServerChannel: Channel, StatelessServerChannel {
    override val serverId: GenericId
    override val server: StatelessServer get() = super<StatelessServerChannel>.server

    public val groupId: GenericId
}