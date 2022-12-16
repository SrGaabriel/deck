package io.github.srgaabriel.deck.core.stateless

import io.github.srgaabriel.deck.common.util.GenericId

public interface StatelessUser: StatelessEntity {
    public val id: GenericId
}