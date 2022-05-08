package io.github.deck.core.stateless.channel

import io.github.deck.common.util.GenericId
import io.github.deck.core.stateless.StatelessEntity
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.util.BlankStatelessServer
import java.util.*

public interface StatelessChannel: StatelessEntity {
    public val id: UUID
    public val serverId: GenericId?

    public val server: StatelessServer? get() = serverId?.let { BlankStatelessServer(client, it) }
}