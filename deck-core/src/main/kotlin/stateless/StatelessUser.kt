package io.github.srgaabriel.deck.core.stateless

import io.github.srgaabriel.deck.common.util.DeckUnsupported
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.core.entity.User

public interface StatelessUser: StatelessEntity {
    public val id: GenericId

    @DeckUnsupported
    public suspend fun fetch(): User =
        client.getUser(id)
}