package io.github.deck.core.entity

import io.github.deck.common.util.GenericId
import io.github.deck.core.stateless.StatelessDocumentation
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.util.BlankStatelessUser
import io.github.deck.rest.builder.CreateDocumentationRequestBuilder
import kotlinx.datetime.Instant

public interface Documentation: StatelessDocumentation {
    public val title: String
    public val content: String

    public val createdAt: Instant
    public val updatedAt: Instant?

    public val authorId: GenericId
    public val editorId: GenericId?

    public val author: StatelessUser get() = BlankStatelessUser(client, authorId)
    public val editor: StatelessUser? get() = editorId?.let { BlankStatelessUser(client, it) }

    public suspend fun patch(builder: CreateDocumentationRequestBuilder.() -> Unit): Documentation =
        update {
            title = this@Documentation.title
            content = this@Documentation.content
            builder()
        }
}