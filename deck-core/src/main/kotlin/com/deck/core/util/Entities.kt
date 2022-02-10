package com.deck.core.util

import com.deck.common.content.Content
import com.deck.common.content.ContentBuilder
import com.deck.common.content.EmbedBuilder
import com.deck.common.content.node.Node
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.builder.DeckMessageBuilder
import com.deck.core.entity.Message
import com.deck.core.entity.channel.ForumPost
import com.deck.core.entity.channel.ForumThread
import com.deck.core.stateless.StatelessMember
import com.deck.core.stateless.StatelessMessage
import com.deck.core.stateless.StatelessRole
import com.deck.core.stateless.channel.StatelessForumChannel
import com.deck.core.stateless.channel.StatelessForumPost
import com.deck.core.stateless.channel.StatelessMessageChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

public data class MemberData(
    public val id: GenericId,
    public val teamId: GenericId
)

public suspend fun StatelessMessageChannel.sendMessage(text: String): Message = sendContent {
    + text
}

public suspend fun StatelessMessageChannel.sendEmbed(
    additionalText: String? = null,
    builder: EmbedBuilder.() -> Unit
): Message = sendContent {
    if (additionalText != null)
        + additionalText
    embed(builder)
}

public suspend fun StatelessMessageChannel.sendContent(builder: ContentBuilder.() -> Unit): Message = sendMessage {
    content(builder)
}

public suspend fun StatelessMessageChannel.sendUnreifiedMessage(builder: DeckMessageBuilder.() -> Unit): StatelessMessage {
    coroutineScope {
        launch {
            sendMessage(builder)
        }
    }
    return BlankStatelessMessage(client, id, this)
}

public suspend fun StatelessMessage.sendUnreifiedReply(builder: DeckMessageBuilder.() -> Unit): StatelessMessage {
    coroutineScope {
        launch {
            sendReply(builder)
        }
    }
    return BlankStatelessMessage(client, id, this.channel)
}

public suspend fun StatelessForumChannel.createThread(title: String, content: ContentBuilder.() -> Unit): ForumThread = createThread {
    this.title = title
    this.content(content)
}

public suspend fun StatelessForumPost.createReplyOfContent(builder: ContentBuilder.() -> Unit): ForumPost = createReply {
    content(builder)
}

internal suspend fun StatelessForumPost.createQuotingReply(
    postId: IntGenericId,
    postContent: Content,
    postCreatedBy: GenericId,
    builder: ContentBuilder.() -> Unit
): ForumPost = createReplyOfContent {
    quote {
        paragraph {
            +Node.Quote.ReplyingToUserHeader(postId, postCreatedBy)
        }
        postContent.nodes.toList()
            .filter { it !is Node.Quote }
            .map { if (it is Node.Paragraph) Node.Paragraph(it.data.children, true) else it }
            .forEach { it.unaryPlus() }
    }
    builder(this)
}

public suspend fun ForumPost.createQuotingReply(builder: ContentBuilder.() -> Unit): ForumPost =
    createQuotingReply(id, content, author.id, builder)

public suspend fun ForumThread.createQuotingReply(builder: ContentBuilder.() -> Unit): ForumPost =
    originalPost.createQuotingReply(builder)

public suspend fun StatelessMember.addRole(role: StatelessRole): Unit = addRole(role.id)

public suspend fun StatelessMember.removeRole(role: StatelessRole): Unit = removeRole(role.id)