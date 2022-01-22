package com.deck.core.stateless

import com.deck.common.util.GenericId
import com.deck.core.entity.Team

public interface StatelessTeam: StatelessEntity<Team> {
    public val id: GenericId

    override suspend fun getState(): Team {
        return client.entityDelegator.getTeam(id)
            ?: error("Tried to get the state of an invalid guild.")
    }
}
