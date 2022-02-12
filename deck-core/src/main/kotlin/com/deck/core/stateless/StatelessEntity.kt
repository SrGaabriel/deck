package com.deck.core.stateless

import com.deck.core.DeckClient
import com.deck.core.entity.Entity

public interface StatelessEntity<A : Entity> {
    public val client: DeckClient

    /**
     * Retrieves entity with the client's entity delegator.
     *
     * @throws IllegalStateException if entity is missing/null
     * @return the [A] entity if found
     */
    public suspend fun getState(): A
}