package com.deck.core.entity

import com.deck.common.util.GenericId
import com.deck.core.stateless.StatelessListItem
import com.deck.core.stateless.StatelessUser
import com.deck.core.util.BlankStatelessUser
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