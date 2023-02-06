package io.github.srgaabriel.deck.core.entity

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.core.stateless.StatelessCalendarEventComment
import io.github.srgaabriel.deck.core.stateless.StatelessUser
import io.github.srgaabriel.deck.core.util.BlankStatelessUser
import kotlinx.datetime.Instant

/**
 * Represents a comment posted in a [CalendarEvent]
 */
public interface CalendarEventComment: StatelessCalendarEventComment {
    /** The comment's content */
    public val content: String

    /** The id of this comment's author */
    public val authorId: GenericId
    public val author: StatelessUser get() = BlankStatelessUser(client, authorId)

    /** The date when this comment was created */
    public val createdAt: Instant
    /** The date when this comment was updated, if it was ever updated */
    public val updatedAt: Instant?
}