package com.deck.core.stateless

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.util.BlankStatelessServer

public interface StatelessRole: StatelessEntity {
    public val id: IntGenericId
    public val serverId: GenericId

    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    public suspend fun awardXp(amount: Int): Unit =
        client.rest.role.awardXpToRole(id, serverId, amount)
}