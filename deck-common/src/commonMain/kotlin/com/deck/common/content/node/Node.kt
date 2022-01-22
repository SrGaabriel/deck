package com.deck.common.content.node

import com.deck.common.content.Embed
import com.deck.common.util.GenericId

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

    public class SystemMessage(public val messageData: SystemMessageData) : Node(
        `object` = "block",
        type = "systemMessage",
        data = NodeData()
    )

    public class BlockQuoteLine(public val blockQuoteLineData: BlockQuoteLineData) : Node(
        `object` = "block",
        type = "block-quote-line",
        data = NodeData()
    )
}

public data class NodeData(
    val text: String? = null,
    val image: String? = null,
    val embeds: List<Embed>? = null
)

public data class SystemMessageData(
    public val type: String,
    public val createdBy: GenericId? = null
)

public data class BlockQuoteLineData(
    public val texts: List<String>?,
    public val marks: List<List<Unit>>?
)
