package io.github.srgaabriel.deck.core.entity

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.core.entity.channel.DocumentationChannel
import io.github.srgaabriel.deck.core.stateless.StatelessDocumentation
import io.github.srgaabriel.deck.core.stateless.StatelessUser
import io.github.srgaabriel.deck.core.util.BlankStatelessUser
import io.github.srgaabriel.deck.rest.builder.CreateDocumentationRequestBuilder
import kotlinx.datetime.Instant

/**
 * A documentation page from a [DocumentationChannel]
 */
public interface Documentation: StatelessDocumentation {
    /** Documentation page title */
    public val title: String
    /** Documentation page content */
    public val content: String

    /** When was the documentation page created */
    public val createdAt: Instant
    /** When was the documentation page last updated */
    public val updatedAt: Instant?

    /** The id of the author of the documentation page */
    public val authorId: GenericId
    /** The id of the user who last updated the documentation page, null if never updated */
    public val editorId: GenericId?

    public val author: StatelessUser get() = BlankStatelessUser(client, authorId)
    public val editor: StatelessUser? get() = editorId?.let { BlankStatelessUser(client, it) }

    /**
     * Patches **(NOT UPDATES)** this documentation
     *
     * @param builder patch builder
     *
     * @return the updated documentation
     */
    public suspend fun patch(builder: CreateDocumentationRequestBuilder.() -> Unit): Documentation =
        update {
            title = this@Documentation.title
            content = this@Documentation.content
            builder()
        }
}