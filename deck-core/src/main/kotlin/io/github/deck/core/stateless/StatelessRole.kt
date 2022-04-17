package io.github.deck.core.stateless

import io.github.deck.common.util.GenericId
import io.github.deck.common.util.IntGenericId
import io.github.deck.core.util.BlankStatelessServer

public interface StatelessRole: StatelessEntity {
    public val id: IntGenericId
    public val serverId: GenericId

    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    public suspend fun awardXp(amount: Int): Unit =
        client.rest.server.awardXpToRole(id, serverId, amount)
}