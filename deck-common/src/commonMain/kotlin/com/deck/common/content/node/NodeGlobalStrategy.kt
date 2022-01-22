package com.deck.common.content.node

import com.deck.common.content.Content
import com.deck.common.content.Embed
import com.deck.common.content.contentBuilder
import com.deck.common.content.from
import com.deck.common.entity.RawMessageContent
import com.deck.common.entity.RawMessageContentData
import com.deck.common.entity.RawMessageContentNode
import com.deck.common.entity.RawMessageContentNodeLeaves
import com.deck.common.util.asNullable
import com.deck.common.util.nullableOptional
import com.deck.common.util.optional

public object NodeGlobalStrategy {
    public fun encodeNode(node: Node): RawMessageContentNode {
        val embeds = node.data.embeds?.map { it.toSerializable() }.nullableOptional()
        val data: RawMessageContentData = when(node) {
            is Node.Text -> RawMessageContentData()
            is Node.Embed -> RawMessageContentData(embeds = embeds)
            is Node.Image -> RawMessageContentData(src = node.data.image!!.optional())
        }
        val leaf = RawMessageContentNodeLeaves(
            leavesObject = "leaf",
            text = node.data.text.orEmpty(),
            marks = emptyList()
        )
        return RawMessageContentNode(
            documentObject = node.`object`,
            type = node.type.optional(),
            data = data,
            nodes = listOf(RawMessageContentNode(leaves = listOf(leaf).optional(), documentObject = "text"))
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
        val leaf =  node.nodes[0].leaves.asNullable()?.getOrNull(0)
        val image = node.data.src.asNullable()
        val embeds = node.data.embeds.asNullable()?.map { Embed.from(it) }
        return when (node.type.asNullable()) {
            "paragraph" -> Node.Text(text = leaf!!.text)
            "webhookMessage" -> Node.Embed(embeds = embeds.orEmpty())
            "image" -> Node.Image(image = image!!)
            else -> null
        }
    }

    public fun decodeContent(content: RawMessageContent): Content = contentBuilder {
        for (node in content.document.nodes) {
            + (decodeNode(node) ?: continue)
        }
    }
}
