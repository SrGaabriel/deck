package com.deck.core.stateless

import com.deck.core.DeckClient
import com.deck.core.entity.Entity

public interface StatelessEntity<A : Entity> {
    public val client: DeckClient

    public suspend fun getState(): A
}