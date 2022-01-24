package com.deck.common.content.node

import com.deck.common.content.Content
import com.deck.common.content.Embed
import com.deck.common.content.contentBuilder
import com.deck.common.content.from
import com.deck.common.entity.*
import com.deck.common.util.OptionalProperty
import com.deck.common.util.asNullable
import com.deck.common.util.nullableOptional
import com.deck.common.util.optional

public object NodeGlobalStrategy {
    public fun encodeNode(node: Node): RawMessageContentNode {
        val embeds = node.data.embeds?.map { it.toSerializable() }.nullableOptional()
        val data: RawMessageContentData = when(node) {
            is Node.Paragraph -> RawMessageContentData()
            is Node.Paragraph.Text -> RawMessageContentData()
            is Node.Paragraph.Link -> RawMessageContentData(href = node.data.link!!.optional())
            is Node.Paragraph.Reaction -> RawMessageContentData(reaction = RawReaction(id = node.id.optional(), customReactionId = node.id).optional())
            is Node.Embed -> RawMessageContentData(embeds = embeds)
            is Node.Image -> RawMessageContentData(src = node.data.image!!.optional())
            is Node.SystemMessage -> RawMessageContentData()
            is Node.Quote -> RawMessageContentData()
        }
        val leaf: String? = when(node) {
            is Node.Paragraph -> null
            is Node.Paragraph.Link -> null
            is Node.Quote -> null
            else -> node.data.text.orEmpty()
        }
        val leaves = if (leaf != null) listOf(RawMessageContentNodeLeaves(
            leavesObject = "leaf",
            text = leaf,
            marks = emptyList()
        )) else emptyList()
        val type = if (node.data.insideQuoteBlock) "block-quote-line" else node.type
        val children: MutableList<RawMessageContentNode> = node.data.children
            .map { encodeNode(it) }
            .toMutableList()
        return RawMessageContentNode(
            documentObject = node.`object`,
            type = if (type.isEmpty()) OptionalProperty.NotPresent else type.optional(),
            data = data,
            nodes = children,
            leaves = leaves.optional()
        )
    }

    public fun encodeContent(content: Content): RawMessageContent {
        return RawMessageContent(
            contentObject = "value",
            document = RawMessageContentNode(
                documentObject = "document",
                data = RawMessageContentData(),
                nodes = content.nodes.map(::encodeNode)
            )
        )
    }

    public fun decodeNode(node: RawMessageContentNode): Node? {
        val leaf = node.nodes.firstOrNull()?.leaves?.asNullable()?.firstOrNull()
        val image = node.data.src.asNullable()
        val embeds = node.data.embeds.asNullable()?.map { Embed.from(it) }
        val children = node.nodes

        return when (node.type.asNullable()) {
            "paragraph" -> {
                Node.Paragraph(content = children.mapNotNull { decodeNode(it) })
            }
            null -> {
                Node.Paragraph.Text(node.leaves.asNullable()?.getOrNull(0)?.text!!)
            }
            "link" -> {
                Node.Paragraph.Link(node.data.href.asNullable()!!)
            }
            "reaction" -> {
                Node.Paragraph.Reaction(node.data.reaction.asNullable()!!.customReactionId)
            }
            "webhookMessage" -> Node.Embed(embeds = embeds.orEmpty())
            "image" -> Node.Image(image = image!!)
            "systemMessage" -> Node.SystemMessage(
                messageData = SystemMessageData(
                    node.type.asNullable()!!,
                    null
                )
            )
            "block-quote-container" -> {
                val lines: List<Node> = node.nodes
                    .mapNotNull { decodeNode(it) }
                Node.Quote(lines = lines)
            }
            "block-quote-line" -> {
                Node.Paragraph(content = listOf(Node.Paragraph.Text(text = leaf?.text?: return null)), insideQuoteBlock = true)
            }
            else -> null
        }
    }

    public fun decodeContent(content: RawMessageContent): Content = contentBuilder {
        for (node in content.document.nodes) {
            + (decodeNode(node) ?: continue)
        }
    }
}