package com.deck.core.util

import com.deck.common.content.Content
import com.deck.common.content.ContentBuilder
import com.deck.common.content.EmbedBuilder
import com.deck.common.content.node.Node
import com.deck.common.entity.RawMessageNodeReplyToUserHeaderType
import com.deck.common.util.Emoji
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.entity.Message
import com.deck.core.entity.channel.ForumPost
import com.deck.core.entity.channel.ForumThread
import com.deck.core.stateless.StatelessMember
import com.deck.core.stateless.StatelessMessage
import com.deck.core.stateless.StatelessRole
import com.deck.core.stateless.channel.StatelessForumChannel
import com.deck.core.stateless.channel.StatelessForumPost
import com.deck.core.stateless.channel.StatelessMessageChannel

/**
 * Sends a plain text message without markdown or anything,
 * it simply adds a paragraph node with a text node of [text] leaf.
 *
 * @param text plain text
 * @return the created message
 */
public suspend fun StatelessMessageChannel.sendMessage(text: String): Message = sendContent {
    + text
}

/**
 * Sends the built embed with the [additionalText] on the top.
 *
 * @param additionalText text on top of the embed
 * @param builder embed below [additionalText]
 *
 * @return the created message
 */
public suspend fun StatelessMessageChannel.sendEmbed(
    additionalText: String? = null,
    builder: EmbedBuilder.() -> Unit
): Message = sendContent {
    if (additionalText != null)
        + additionalText
    embed(builder)
}

/**
 * Simply sends a message without nesting your code with
 * the content DSL.
 *
 * @param builder message content
 * @return the created message
 */
public suspend fun StatelessMessageChannel.sendContent(builder: ContentBuilder.() -> Unit): Message = sendMessage {
    content(builder)
}

/**
 * Shortcut for creating threads of [title] title and content [content]
 *
 * @param title thread title
 * @param content original post content
 *
 * @return the created forum thread
 */
public suspend fun StatelessForumChannel.createThread(title: String, content: ContentBuilder.() -> Unit): ForumThread = createThread {
    this.title = title
    this.content(content)
}

/**
 * Creates a reply for the post (without quoting it, see [createQuotingReply]), with
 * the built content.
 *
 * @see createQuotingReply
 * @param builder content
 *
 * @return the created forum post
 */
public suspend fun StatelessForumPost.createReplyOfContent(builder: ContentBuilder.() -> Unit): ForumPost = createReply {
    content(builder)
}

/**
 * Creates a forum post reply quoting the specified post.
 *
 * @param postId quoted post id
 * @param postContent quote content
 * @param postCreatedBy quote author
 * @param builder reply content after nodes
 *
 * @return the created forum post
 */
internal suspend fun StatelessForumPost.createQuotingReply(
    postId: IntGenericId,
    postContent: Content,
    postCreatedBy: GenericId,
    builder: ContentBuilder.() -> Unit
): ForumPost = createReplyOfContent {
    quote {
        paragraph {
            + Node.ReplyHeader(RawMessageNodeReplyToUserHeaderType.QUOTE, postId, postCreatedBy)
        }
        + postContent.nodes.toList()
            .filterIsInstance<Node.Paragraph>()
            .map { Node.Paragraph(it.data.children, true) }
    }
    builder(this)
}

/**
 * Creates a quoting reply to a defined forum post, already specifying for
 * you the post's id ([ForumPost.id]), content ([ForumPost.content]) and ([ForumPost.author]).
 *
 * @see createQuotingReply
 * @param builder content
 *
 * @return the created forum post
 */
public suspend fun ForumPost.createQuotingReply(builder: ContentBuilder.() -> Unit): ForumPost =
    createQuotingReply(id, content, author.id, builder)

/**
 * Shortcut to create a reply quoting the original post [ForumThread.originalPost].
 *
 * @see createQuotingReply
 * @param builder content
 *
 * @return the created forum post
 */
public suspend fun ForumThread.createQuotingReply(builder: ContentBuilder.() -> Unit): ForumPost =
    originalPost.createQuotingReply(builder)

/**
 * Creates a forum post reply referencing the specified post.
 *
 * @param postId quoted post id
 * @param postCreatedBy quote author
 * @param builder reply content after nodes
 *
 * @return the created forum post
 */
internal suspend fun StatelessForumPost.createReferencingReply(
    postId: IntGenericId,
    postCreatedBy: GenericId,
    builder: ContentBuilder.() -> Unit
): ForumPost = createReplyOfContent {
    + Node.ReplyHeader(RawMessageNodeReplyToUserHeaderType.REFERENCE, postId, postCreatedBy)
    builder(this)
}

/**
 * Creates a reply with a reference to this forum post, already specifying for
 * you the post's id ([ForumPost.id]) and ([ForumPost.author]).
 *
 * @see createQuotingReply
 * @param builder content
 *
 * @return the created forum post
 */
public suspend fun ForumPost.createReferencingReply(builder: ContentBuilder.() -> Unit): ForumPost =
    createReferencingReply(id, author.id, builder)

/**
 * Shortcut to create a reply referencing the original post [ForumThread.originalPost].
 *
 * @see createQuotingReply
 * @param builder content
 *
 * @return the created forum post
 */
public suspend fun ForumThread.createReferencingReply(builder: ContentBuilder.() -> Unit): ForumPost =
    originalPost.createReferencingReply(builder)

/**
 * Adds the [role] to the member by calling [StatelessMember.addRole] with the specified role id.
 *
 * @param role role to be assigned
 */
public suspend fun StatelessMember.addRole(role: StatelessRole): Unit = addRole(role.id)

/**
 * Removes the [role] from the member by calling [StatelessMember.addRole] with the specified role id.
 *
 * @param role role to be removed
 */
public suspend fun StatelessMember.removeRole(role: StatelessRole): Unit = removeRole(role.id)

/**
 * Adds the [emoji] reaction to the message by calling [StatelessMessage.addReaction] with the specified emoji id.
 *
 * @param emoji emoji to be added
 */
public suspend fun StatelessMessage.addReaction(emoji: Emoji): Unit =
    addReaction(reactionId = emoji.id)

/**
 * Removes the [emoji] reaction from the message by calling [StatelessMessage.addReaction] with the specified emoji id.
 *
 * @param emoji emoji to be removed
 */
public suspend fun StatelessMessage.removeReaction(emoji: Emoji): Unit =
    removeReaction(reactionId = emoji.id)