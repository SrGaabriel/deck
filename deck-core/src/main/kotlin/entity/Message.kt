package io.github.srgaabriel.deck.core.entity

import io.github.srgaabriel.deck.common.Embed
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.core.entity.impl.DeckMessage
import io.github.srgaabriel.deck.core.stateless.StatelessMessage
import io.github.srgaabriel.deck.core.stateless.StatelessUser
import io.github.srgaabriel.deck.core.stateless.StatelessWebhook
import io.github.srgaabriel.deck.core.util.BlankStatelessUser
import io.github.srgaabriel.deck.core.util.BlankStatelessWebhook
import io.github.srgaabriel.deck.rest.builder.SendMessageRequestBuilder
import io.github.srgaabriel.deck.rest.builder.UpdateMessageRequestBuilder
import kotlinx.datetime.Instant
import java.util.*

/**
 * This represents a guilded chat message.
 * This interface's default implementation is [DeckMessage].
 */
public interface Message : StatelessMessage {
    /** This message's raw content, may be empty */
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

    /** Message mentions, null if there aren't any */
    public val mentions: Mentions?

    /** List of all message IDs this message is replying to */
    public val repliesTo: List<UUID>
    /** Whether this message is a private reply (only mentioned and replied to users can see it) */
    public val isPrivate: Boolean
    /** Whether this message is silent (does not notify mentioned users) */
    public val isSilent: Boolean

    /** The id of the webhook that created this message, if one */
    public val createdByWebhookId: UUID?
    public val createdByWebhook: StatelessWebhook? get() = createdByWebhookId?.let { BlankStatelessWebhook(client, it, serverId ?: error("Message was created by a webhook but not in a server")) }

    /**
     * Patches **(NOT UPDATES)** this message
     *
     * @param builder patch builder
     *
     * @return the updated message
     */
    public suspend fun patch(builder: UpdateMessageRequestBuilder.() -> Unit): Message = update {
        content = this@Message.content
        embeds = this@Message.embeds
        builder()
    }

    override suspend fun sendReply(builder: SendMessageRequestBuilder.() -> Unit): Message = super.sendReply {
        isPrivate = client.privateRepliesToPrivateMessagesByDefault && this@Message.isPrivate
        builder()
    }
}