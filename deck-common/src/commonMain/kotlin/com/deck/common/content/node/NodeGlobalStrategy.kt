package com.deck.common.content.node

import com.deck.common.content.Content
import com.deck.common.entity.RawMessageContent
import com.deck.common.entity.RawMessageContentData
import com.deck.common.entity.RawMessageContentNode
import com.deck.common.entity.RawMessageContentNodeLeaves
import com.deck.common.util.asNullable
import com.deck.common.util.optional

public object NodeGlobalStrategy {
    public fun encodeNode(node: Node): RawMessageContentNode {
        val data: RawMessageContentData = when (node) {
            is Node.Text -> RawMessageContentData()
            is Node.Image -> RawMessageContentData(src = node.data.image!!.optional())
            is Node.SystemMessage -> RawMessageContentData(node.data.text!!.optional())
        }
        return RawMessageContentNode(
            documentObject = node.`object`,
            type = node.type.optional(),
            data = data,
            nodes = listOf(
                RawMessageContentNode(
                    leaves = listOf(
                        RawMessageContentNodeLeaves(
                            leavesObject = "leaf",
                            text = node.data.text.orEmpty(),
                            marks = emptyList()
                        )
                    ).optional(), documentObject = "text"
                )
            )
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
        return when (node.type.asNullable()) {
            "paragraph" -> Node.Text(text = node.nodes[0].leaves.asNullable()!![0].text)
            "image" -> Node.Image(image = node.data.src.asNullable()!!)
            "systemMessage" -> Node.SystemMessage(
                messageData = SystemMessageData(
                    node.type.asNullable()!!,
                    null
                )
            )
            else -> null
        }
    }

    public fun decodeContent(content: RawMessageContent): Content = Content().also { wrapper ->
        for (node in content.document.nodes) {
            wrapper.addNode(decodeNode(node) ?: continue)
        }
    }
}
