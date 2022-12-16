package io.github.srgaabriel.deck.core.entity.channel

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.channel.StatelessMessageChannel

public interface MessageChannel: Channel, StatelessMessageChannel

public interface DmChannel: Channel, StatelessMessageChannel {
    override val serverId: GenericId? get() = null
    override val server: StatelessServer? get() = null
}

public interface ServerMessageChannel: ServerChannel, StatelessMessageChannel