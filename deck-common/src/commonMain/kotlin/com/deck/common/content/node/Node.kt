package com.deck.common.content.node

public sealed class Node(
    public val `object`: String,
    public val type: String,
    public val data: NodeData
) {
    public class Text(text: String) : Node(
        `object` = "block",
        type = "paragraph",
        data = NodeData(text = text)
    )

    public class Image(image: String) : Node(
        `object` = "block",
        type = "image",
        data = NodeData(image = image)
    )
}

public data class NodeData(
    val text: String? = null,
    val image: String? = null
)
