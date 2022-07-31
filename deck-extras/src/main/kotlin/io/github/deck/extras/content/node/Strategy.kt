package io.github.deck.extras.content.node

import io.github.deck.common.Embed
import io.github.deck.common.from
import io.github.deck.common.util.OptionalProperty
import io.github.deck.common.util.asNullable
import io.github.deck.common.util.optional
import io.github.deck.extras.content.*

public fun Content.encode(): RawMessageContent = RawMessageContent(
    contentObject = "value",
    document = RawMessageContentNode(
        documentObject = "document",
        data = RawMessageContentData(),
        nodes = nodes.map(Node::encode)
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
        is Node.Quote -> RawMessageContentData()
        is Node.CodeBlock -> RawMessageContentData(language = language.optional())
        is Node.CodeBlock.Line -> RawMessageContentData()
        is Node.Lists.Item -> RawMessageContentData()
        is Node.Lists.Bulleted -> RawMessageContentData(isList = true.optional())
        is Node.Lists.Numbered -> RawMessageContentData(isList = true.optional())
        is Node.Mention -> RawMessageContentData(mention = RawMentionData(id = id, type = mentionType, name = "MentionTest".optional(), matcher = "@MentionTest".optional()).optional())
        is Node.Mention.Channel -> RawMessageContentData(channel = RawMentionedChannel(id = id).optional())
    }
    val leaves = this.data.leaves?.map {
        RawMessageContentNodeLeaves(
            leavesObject = "leaf",
            text = it.text,
            marks = it.marks.map { markType ->
                RawMessageContentNodeLeavesMark(
                    markObject = "mark",
                    type = markType,
                    data = RawMessageContentData()
                )
            }
        )
    }?.optional() ?: OptionalProperty.NotPresent
    val type = if (this.data.insideQuoteBlock) RawMessageContentNodeType.BLOCK_QUOTE_LINE else this.type
    val children: MutableList<RawMessageContentNode> = this.data.children
        .map { it.encode() }
        .toMutableList()
    return RawMessageContentNode(
        documentObject = `object`,
        type = type,
        data = data,
        nodes = children,
        leaves = leaves
    )
}

public fun RawMessageContent.decode(): Content =
    Content(document.nodes.mapNotNull(RawMessageContentNode::decode))

public fun RawMessageContentNode.decode(): Node? {
    val leaves = leaves.asNullable()?.map { Node.Paragraph.Text.Leaf(it.text, it.marks.map { mark -> mark.type }) }
    val image = data.src.asNullable()
    val embeds = data.embeds.asNullable()?.map { Embed.from(it) }
    return when (type) {
        RawMessageContentNodeType.PARAGRAPH ->
            Node.Paragraph(content = nodes.mapNotNull(RawMessageContentNode::decode))
        RawMessageContentNodeType.MARKDOWN_PLAIN_TEXT ->
            Node.Paragraph(content = nodes.mapNotNull(RawMessageContentNode::decode))
        RawMessageContentNodeType.LINK ->
            Node.Paragraph.Link(data.href.asNullable()!!, leaves?.joinToString { it.text } ?: return null)
        RawMessageContentNodeType.REACTION ->
            Node.Paragraph.Reaction(data.reaction.asNullable()!!.customReactionId)
        RawMessageContentNodeType.WEBHOOK_MESSAGE ->
            Node.Embed(embeds = embeds.orEmpty())
        RawMessageContentNodeType.IMAGE ->
            Node.Image(image = image!!)
        RawMessageContentNodeType.BLOCK_QUOTE_CONTAINER ->
            Node.Paragraph(content = nodes.mapNotNull(RawMessageContentNode::decode), insideQuoteBlock = true)
        RawMessageContentNodeType.BLOCK_QUOTE_LINE ->
            Node.Paragraph(content = listOf(Node.Paragraph.Text(leaves = leaves ?: return null)),
                insideQuoteBlock = true)
        RawMessageContentNodeType.BLANK ->
            Node.Paragraph.Text(leaves ?: return null)
        RawMessageContentNodeType.CODE_BLOCK ->
            Node.CodeBlock(data.language.asNullable()!!,
                nodes.mapNotNull(RawMessageContentNode::decode).filterIsInstance<Node.CodeBlock.Line>())
        RawMessageContentNodeType.CODE_LINE ->
            Node.CodeBlock.Line(nodes.firstOrNull()?.decode()?.data?.leaves?.firstOrNull()?.text ?: return null)
        RawMessageContentNodeType.BULLETED_LIST ->
            Node.Lists.Bulleted(nodes.mapNotNull(RawMessageContentNode::decode).filterIsInstance<Node.Lists.Item>())
        RawMessageContentNodeType.NUMBERED_LIST ->
            Node.Lists.Numbered(nodes.mapNotNull(RawMessageContentNode::decode).filterIsInstance<Node.Lists.Item>())
        RawMessageContentNodeType.LIST_ITEM ->
            Node.Lists.Item(nodes.mapNotNull(RawMessageContentNode::decode))
        RawMessageContentNodeType.MENTION ->
            data.mention.asNullable()?.let { Node.Mention(it.id, it.type) }!!
        RawMessageContentNodeType.MENTION_CHANNEL ->
            Node.Mention.Channel(data.channel.asNullable()!!.id)
    }
}