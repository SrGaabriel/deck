package com.deck.core.stateless

import com.deck.common.util.GenericId
import com.deck.core.entity.User

public interface StatelessUser: StatelessEntity<User> {
    public val id: GenericId

    override suspend fun getState(): User {
        return client.entityDelegator.getUser(id)
            ?: error("Tried to get the state of an invalid user.")
    }
}
