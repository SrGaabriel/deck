package com.deck.common.content.node

import com.deck.common.content.Content
import com.deck.common.content.Embed
import com.deck.common.content.contentBuilder
import com.deck.common.content.from
import com.deck.common.entity.*
import com.deck.common.util.asNullable
import com.deck.common.util.optional

public fun Content.encode(): RawMessageContent = RawMessageContent(
    contentObject = "value",
    document = RawMessageContentNode(
        documentObject = "document",
        data = RawMessageContentData(),
        nodes = this.nodes.map { it.encode() }
    )
)

public fun Node.encode(): RawMessageContentNode {
    val data: RawMessageContentData = when(this) {
        is Node.Paragraph -> RawMessageContentData()
        is Node.Paragraph.Text -> RawMessageContentData()
        is Node.Paragraph.Link -> RawMessageContentData(href = link.optional())
        is Node.Paragraph.Reaction -> RawMessageContentData(reaction = RawReaction(id = id.optional(), customReactionId = id).optional())
        is Node.Embed -> RawMessageContentData(embeds = embeds.map { it.toSerializable() }.optional())
        is Node.Image -> RawMessageContentData(src = image.optional())
        is Node.SystemMessage -> RawMessageContentData()
        is Node.Quote -> RawMessageContentData()
    }
    val leaves = if (this.data.text != null) listOf(RawMessageContentNodeLeaves(
        leavesObject = "leaf",
        text = this.data.text,
        marks = emptyList()
    )) else emptyList()
    val type = if (this.data.insideQuoteBlock) RawMessageContentNodeType.BLOCK_QUOTE_LINE else this.type
    val children: MutableList<RawMessageContentNode> = this.data.children
        .map { it.encode() }
        .toMutableList()
    return RawMessageContentNode(
        documentObject = `object`,
        type = type,
        data = data,
        nodes = children,
        leaves = leaves.optional()
    )
}

public fun RawMessageContent.decode(): Content = contentBuilder {
    for (node in document.nodes) {
        (node.decode() ?: continue).unaryPlus()
    }
}

public fun RawMessageContentNode.decode(): Node? {
    val leaf = nodes.firstOrNull()?.leaves?.asNullable()?.firstOrNull()
    val image = data.src.asNullable()
    val embeds = data.embeds.asNullable()?.map { Embed.from(it) }

    return when (type) {
        RawMessageContentNodeType.PARAGRAPH ->
            Node.Paragraph(content = nodes.mapNotNull(RawMessageContentNode::decode))
        RawMessageContentNodeType.LINK ->
            Node.Paragraph.Link(data.href.asNullable()!!)
        RawMessageContentNodeType.REACTION ->
            Node.Paragraph.Reaction(data.reaction.asNullable()!!.customReactionId)
        RawMessageContentNodeType.WEBHOOK_MESSAGE ->
            Node.Embed(embeds = embeds.orEmpty())
        RawMessageContentNodeType.IMAGE ->
            Node.Image(image = image!!)
        RawMessageContentNodeType.SYSTEM_MESSAGE ->
            Node.SystemMessage(messageData = SystemMessageData("", null))
        RawMessageContentNodeType.BLOCK_QUOTE_CONTAINER ->
            Node.Paragraph(content = nodes.mapNotNull(RawMessageContentNode::decode), insideQuoteBlock = true)
        RawMessageContentNodeType.BLOCK_QUOTE_LINE ->
            Node.Paragraph(content = listOf(Node.Paragraph.Text(text = leaf?.text?: return null)), insideQuoteBlock = true)
        RawMessageContentNodeType.BLANK ->
            Node.Paragraph.Text(leaves.asNullable()?.getOrNull(0)?.text!!)
    }
}