package io.github.deck.core.stateless

import io.github.deck.common.util.GenericId

public interface StatelessUser: StatelessEntity {
    public val id: GenericId
}