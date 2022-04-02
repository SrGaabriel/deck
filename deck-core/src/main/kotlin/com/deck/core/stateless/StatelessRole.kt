package com.deck.core.stateless

import com.deck.common.util.IntGenericId

public interface StatelessRole {
    public val id: IntGenericId
    public val server: StatelessServer

    public suspend fun awardXp(amount: Int): Unit =
        server.client.rest.role.awardXpToRole(id, server.id, amount)
}