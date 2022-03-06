package com.deck.core.entity

import com.deck.common.content.Content
import com.deck.common.util.GenericId
import com.deck.core.stateless.StatelessMessage
import com.deck.core.stateless.StatelessUser
import com.deck.core.util.BlankStatelessUser
import kotlinx.datetime.Instant
import java.util.*

public interface Message : Entity, StatelessMessage {
    public val content: Content

    public val repliesToId: UUID?

    public val createdAt: Instant
    public val updatedAt: Instant?

    public val authorId: GenericId
    public val editorId: GenericId?

    public val author: StatelessUser get() = BlankStatelessUser(client, authorId)
    public val editor: StatelessUser? get() = editorId?.let { BlankStatelessUser(client, it) }

    public val isPrivate: Boolean
}