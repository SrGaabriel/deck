package com.deck.core.entity

import com.deck.core.stateless.StatelessDocumentation
import com.deck.core.stateless.StatelessServer
import com.deck.core.stateless.StatelessUser
import kotlinx.datetime.Instant

public interface Documentation: Entity, StatelessDocumentation {
    public val title: String
    public val content: String

    public val server: StatelessServer

    public val createdAt: Instant
    public val updatedAt: Instant

    public val createdBy: StatelessUser
    public val updatedBy: StatelessUser
}