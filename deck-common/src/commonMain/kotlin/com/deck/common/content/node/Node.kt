package com.deck.common.content.node

import com.deck.common.entity.RawMessageContentNodeType
import com.deck.common.util.GenericId

public sealed class Node(
    public val `object`: String,
    public val type: RawMessageContentNodeType,
    public val data: NodeData
) {
    public class Paragraph(content: List<Node>, insideQuoteBlock: Boolean = false) : Node(
        `object` = "block",
        type = RawMessageContentNodeType.PARAGRAPH,
        data = NodeData(text = null, children = content, insideQuoteBlock = insideQuoteBlock)
    ) {
        public class Text(public val text: String): Node(
            `object` = "text",
            type = RawMessageContentNodeType.BLANK,
            data = NodeData(text = text)
        )
        public class Link(public val link: String): Node(
            `object` = "inline",
            type = RawMessageContentNodeType.LINK,
            data = NodeData(children = listOf(Text(link)), text = null)
        )
        public class Reaction(public val id: Int): Node(
            `object` = "inline",
            type = RawMessageContentNodeType.REACTION,
            data = NodeData()
        )
    }

    public class Embed(public val embeds: List<com.deck.common.content.Embed>) : Node(
        `object` = "block",
        type = RawMessageContentNodeType.WEBHOOK_MESSAGE,
        data = NodeData()
    )

    public class Image(public val image: String) : Node(
        `object` = "block",
        type = RawMessageContentNodeType.IMAGE,
        data = NodeData()
    )

    public class SystemMessage(public val messageData: SystemMessageData) : Node(
        `object` = "block",
        type = RawMessageContentNodeType.SYSTEM_MESSAGE,
        data = NodeData()
    )

    public class Quote(lines: List<Node>) : Node(
        `object` = "block",
        type = RawMessageContentNodeType.BLOCK_QUOTE_CONTAINER,
        data = NodeData(children = lines)
    )
}

public data class NodeData(
    val text: String? = "",
    val children: List<Node> = emptyList(),
    val insideQuoteBlock: Boolean = false
)

public data class SystemMessageData(
    public val type: String,
    public val createdBy: GenericId? = null
)