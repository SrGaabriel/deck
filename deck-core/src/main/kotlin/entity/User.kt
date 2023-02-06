package io.github.srgaabriel.deck.core.entity

import io.github.srgaabriel.deck.core.stateless.StatelessUser
import kotlinx.datetime.Instant

public interface User: StatelessUser {
    public val name: String

    public val avatar: String?
    public val banner: String?

    public val isBot: Boolean
    public val createdAt: Instant
}