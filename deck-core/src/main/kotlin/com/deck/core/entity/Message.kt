package com.deck.core.entity

import com.deck.common.util.GenericId
import com.deck.core.entity.impl.DeckMessage
import com.deck.core.stateless.StatelessMessage
import com.deck.core.stateless.StatelessUser
import com.deck.core.util.BlankStatelessUser
import kotlinx.datetime.Instant
import java.util.*

/**
 * This represents a guilded chat message.
 * This interface's default implementation is [DeckMessage].
 */
public interface Message : StatelessMessage {
    /** This message's raw content */
    public val content: String

    /** The id of this message's author */
    public val authorId: GenericId
    public val author: StatelessUser get() = BlankStatelessUser(client, authorId)

    /** The instant this message was sent */
    public val createdAt: Instant
    /** The instant this message was last edited, null by default */
    public val updatedAt: Instant?

    /** List of all message IDs this message is replying to */
    public val repliesTo: List<UUID>
    /** Whether this message is a private reply */
    public val isPrivate: Boolean
}