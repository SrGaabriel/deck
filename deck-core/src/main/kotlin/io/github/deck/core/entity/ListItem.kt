package io.github.deck.core.entity

import io.github.deck.common.util.GenericId
import io.github.deck.core.stateless.StatelessListItem
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.util.BlankStatelessUser
import kotlinx.datetime.Instant

public interface ListItem: StatelessListItem {
    public val authorId: GenericId
    public val author: StatelessUser get() = BlankStatelessUser(client, authorId)

    public val label: String
    public val note: String?

    public val createdAt: Instant
    public val updatedAt: Instant?

    public val editorId: GenericId?
    public val editor: StatelessUser? get() = editorId?.let { BlankStatelessUser(client, it) }
}