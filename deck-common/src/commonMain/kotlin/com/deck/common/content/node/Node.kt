package com.deck.common.content.node

sealed class Node(
    val `object`: String,
    val type: String,
    val data: NodeData
) {
    class Text(text: String) : Node(
        `object` = "block",
        type = "paragraph",
        data = NodeData(text = text)
    )

    class Image(image: String) : Node(
        `object` = "block",
        type = "image",
        data = NodeData(image = image)
    )
}

data class NodeData(
    val text: String? = null,
    val image: String? = null
)
