package io.github.deck.core.entity

import io.github.deck.common.Embed
import io.github.deck.common.util.GenericId
import io.github.deck.core.entity.impl.DeckMessage
import io.github.deck.core.stateless.StatelessMessage
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.util.BlankStatelessUser
import io.github.deck.rest.builder.UpdateMessageRequestBuilder
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

    /** All embeds present on this message */
    public val embeds: List<Embed>

    /** The instant this message was sent */
    public val createdAt: Instant
    /** The instant this message was last edited, null by default */
    public val updatedAt: Instant?

    /** List of all message IDs this message is replying to */
    public val repliesTo: List<UUID>
    /** Whether this message is a private reply */
    public val isPrivate: Boolean

    public suspend fun patch(builder: UpdateMessageRequestBuilder.() -> Unit): Message = update {
        content = this@Message.content
        embeds = this@Message.embeds
        builder()
    }
}