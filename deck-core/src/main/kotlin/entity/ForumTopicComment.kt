package io.github.srgaabriel.deck.core.entity

import io.github.srgaabriel.deck.core.stateless.StatelessForumTopicComment
import kotlinx.datetime.Instant

/**
 * Represents a comment posted in a [ForumTopic]
 */
public interface ForumTopicComment: StatelessForumTopicComment {
    /** The comment's content */
    public val content: String
    /** The entities mentioned in this comment, null if none */
    public val mentions: Mentions?

    /** The date when this comment was created */
    public val createdAt: Instant
    /** The date when this comment was updated, if it was ever updated */
    public val updatedAt: Instant?
}