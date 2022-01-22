package com.deck.common.content.node

import com.deck.common.content.Embed

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

    public class Embed(embeds: List<com.deck.common.content.Embed>) : Node(
        `object` = "block",
        type = "webhookMessage",
        data = NodeData(embeds = embeds)
    )

    public class Image(image: String) : Node(
        `object` = "block",
        type = "image",
        data = NodeData(image = image)
    )
}

public data class NodeData(
    val text: String? = null,
    val image: String? = null,
    val embeds: List<Embed>? = null
)
