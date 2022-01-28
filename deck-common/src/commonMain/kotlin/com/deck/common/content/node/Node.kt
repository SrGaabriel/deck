package com.deck.common.content.node

import com.deck.common.entity.RawMessageContentNodeLeavesMarkType
import com.deck.common.entity.RawMessageContentNodeType
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId

public sealed class Node(
    public val `object`: String,
    public val type: RawMessageContentNodeType,
    public val data: NodeData
) {
    public class Paragraph(content: List<Node>, insideQuoteBlock: Boolean = false) : Node(
        `object` = "block",
        type = RawMessageContentNodeType.PARAGRAPH,
        data = NodeData(leaves = null, children = content, insideQuoteBlock = insideQuoteBlock)
    ) {
        public class Text(leaves: List<Leaf>): Node(
            `object` = "text",
            type = RawMessageContentNodeType.BLANK,
            data = NodeData(leaves = leaves)
        ) {
            public data class Leaf(val text: String, val marks: List<RawMessageContentNodeLeavesMarkType> = emptyList())
        }
        public class Link(public val link: String, public val leaf: Text.Leaf = Text.Leaf(link)): Node(
            `object` = "inline",
            type = RawMessageContentNodeType.LINK,
            data = NodeData(children = listOf(Text(listOf(leaf))), leaves = null)
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
        data = NodeData(leaves = null, children = lines)
    ) {
        public class ReplyingToUserHeader(public val postId: IntGenericId, public val postAuthor: GenericId): Node(
            `object` = "block",
            type = RawMessageContentNodeType.REPLYING_TO_USER_HEADER,
            data = NodeData(leaves = null, children = listOf(Paragraph.Text(emptyList())))
        )
    }

    public interface Lists {
        public class Bulleted(override val items: List<Item>): Node(
            `object` = "block",
            type = RawMessageContentNodeType.BULLETED_LIST,
            data = NodeData(leaves = null, children = items)
        ), Lists
        public class Numbered(override val items: List<Item>): Node(
            `object` = "block",
            type = RawMessageContentNodeType.NUMBERED_LIST,
            data = NodeData(leaves = null, children = items)
        ), Lists
        public class Item(children: List<Node>): Node(
            `object` = "block",
            type = RawMessageContentNodeType.LIST_ITEM,
            data = NodeData(leaves = null, children = children)
        )
        public val items: List<Item>
    }

    public class CodeBlock(public val language: String, lines: List<Line>): Node(
        `object` = "block",
        type = RawMessageContentNodeType.CODE_BLOCK,
        data = NodeData(leaves = null, children = lines)
    ) {
        public class Line(text: String): Node(
            `object` = "block",
            type = RawMessageContentNodeType.CODE_LINE,
            data = NodeData(children = listOf(Paragraph.Text(leaves = listOf(Paragraph.Text.Leaf(text)))))
        )
    }
}

public data class NodeData(
    val leaves: List<Node.Paragraph.Text.Leaf>? = emptyList(),
    val children: List<Node> = emptyList(),
    val insideQuoteBlock: Boolean = false
)

public data class SystemMessageData(
    public val type: String,
    public val createdBy: GenericId? = null
)