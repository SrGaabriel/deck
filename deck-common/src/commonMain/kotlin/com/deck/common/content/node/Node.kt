package com.deck.common.content.node

import com.deck.common.content.Embed
import com.deck.common.util.DeckObsoleteApi
import com.deck.common.util.GenericId

public sealed class Node(
    public val `object`: String,
    public val type: String,
    public val data: NodeData
) {
    public class Paragraph(content: List<Node>, insideQuoteBlock: Boolean = false) : Node(
        `object` = "block",
        type = "paragraph",
        data = NodeData(children = content, insideQuoteBlock = insideQuoteBlock)
    ) {
        public class Text(public val text: String): Node(
            `object` = "text",
            type = "",
            data = NodeData(text = text)
        )
        public class Link(link: String): Node(
            `object` = "inline",
            type = "link",
            data = NodeData(children = listOf(Text(link)), text = link, link = link)
        )
        public class Reaction(public val id: Int): Node(
            `object` = "inline",
            type = "reaction",
            data = NodeData()
        )
    }

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

    public class Quote(public val lines: List<Node>) : Node(
        `object` = "block",
        type = "block-quote-container",
        data = NodeData(children = lines)
    )
}

@DeckObsoleteApi
public data class NodeData(
    val text: String? = null,
    val link: String? = null,
    val image: String? = null,
    val embeds: List<Embed>? = null,
    val children: List<Node> = emptyList(),
    val insideQuoteBlock: Boolean = false
)

public data class SystemMessageData(
    public val type: String,
    public val createdBy: GenericId? = null
)