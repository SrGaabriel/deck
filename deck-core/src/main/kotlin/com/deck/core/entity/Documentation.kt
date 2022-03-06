package com.deck.core.entity

import com.deck.common.util.GenericId
import com.deck.core.stateless.StatelessDocumentation
import com.deck.core.stateless.StatelessUser
import com.deck.core.util.BlankStatelessUser
import kotlinx.datetime.Instant

public interface Documentation: Entity, StatelessDocumentation {
    public val title: String
    public val content: String

    public val createdAt: Instant
    public val updatedAt: Instant

    public val authorId: GenericId
    public val editorId: GenericId

    public val author: StatelessUser get() = BlankStatelessUser(client, authorId)
    public val editor: StatelessUser get() = BlankStatelessUser(client, editorId)
}