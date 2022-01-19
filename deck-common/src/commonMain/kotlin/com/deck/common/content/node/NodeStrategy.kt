package com.deck.common.content.node

import com.deck.common.content.Content
import com.deck.common.entity.RawMessageContent
import com.deck.common.entity.RawMessageContentData
import com.deck.common.entity.RawMessageContentNode
import com.deck.common.entity.RawMessageContentNodeLeaves
import com.deck.common.util.asNullable
import com.deck.common.util.optional

object NodeStrategy {
    fun encodeNode(node: Node): RawMessageContentNode {
        val data: RawMessageContentData = when(node) {
            is Node.Text -> RawMessageContentData()
            is Node.Image -> RawMessageContentData(src = node.data.image!!.optional())
        }
        return RawMessageContentNode(
            documentObject = node.`object`,
            type = node.type.optional(),
            data = data,
            nodes = listOf(
                RawMessageContentNode(leaves = listOf(RawMessageContentNodeLeaves(
                    leavesObject = "leaf",
                    text = node.data.text.orEmpty(),
                    marks = emptyList()
                )).optional(), documentObject = "text")
            )
        )
    }

    fun encodeContent(content: Content): RawMessageContent {
        return RawMessageContent(
            contentObject = "value",
            document = RawMessageContentNode(
                documentObject = "document",
                data = RawMessageContentData(),
                nodes = content.nodes.map(::encodeNode)
            )
        )
    }

    fun decodeNode(node: RawMessageContentNode): Node? {
        val leaf =  node.nodes[0].leaves.asNullable()!![0]
        val image = node.data.src.asNullable()
        return when (node.type.asNullable()) {
            "paragraph" -> Node.Text(text = leaf.text)
            "image" -> Node.Image(image = image!!)
            else -> null
        }
    }

    fun decodeContent(content: RawMessageContent): Content = Content().also { wrapper ->
        for (node in content.document.nodes) {
            wrapper.addNode(decodeNode(node) ?: continue)
        }
    }
}
