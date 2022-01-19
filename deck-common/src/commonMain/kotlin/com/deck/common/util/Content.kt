package com.deck.common.util

import com.deck.common.entity.RawMessageContent
import com.deck.common.entity.RawMessageContentData
import com.deck.common.entity.RawMessageContentNode
import com.deck.common.entity.RawMessageContentNodeLeaves

public class ContentBuilder {
    public val nodes: MutableList<Node> = mutableListOf<Node>()
    private val nodeEncoder: NodeEncoder = NodeEncoder()

    /**
     * Simply adds a new node to our list and returning
     * [Unit].
     *
     * @param node The node to be added
     */
    public fun addNode(node: Node): Unit = node.let { nodes.add(it); Unit }

    public fun build(): RawMessageContent = RawMessageContent(
        contentObject = "value",
        document = RawMessageContentNode(
            documentObject = "document",
            data = RawMessageContentData(),
            nodes = nodes.map { nodeEncoder.encodeNode(it) }
        )
    )
}

public class ContentWrapper(public val builder: ContentBuilder = ContentBuilder()) {
    public val add: NodeRegistry = NodeRegistry()

    /**
     * Add a new paragraph node with [text] content skipping
     * a line or a previous node, meaning that you need not provide empty values
     * to skip lines, since each time you call this function you'll be doing exactly that.
     *
     * @param text The written content of the message
     */
    public infix fun NodeRegistry.text(text: String): Unit = builder.addNode(Node.Text(text = text))

    /**
     * Adds an image to the message, this method can be called between
     * other nodes, meaning that you can have images anywhere you want in the message,
     * not only at the bottom.
     *
     * @param image The link of the image you'll be sending.
     */
    public infix fun NodeRegistry.image(image: String): Unit = builder.addNode(Node.Image(image = image))

    /**
     * Adds a link to the message, it's important because it'll inline/embed
     * your link, if you try to send it just using [text], it won't do anything.
     *
     * @param text The text plus the placeholder {link}, which will be replaced
     * by the specified link in [LinkedText].
     */
    public infix fun NodeRegistry.linked(text: String): LinkedText = (LinkedText() + text)

    public inline operator fun invoke(scope: ContentWrapper.() -> Unit): ContentWrapper =
        this.apply(scope)

    public infix fun LinkedText.whereLink(link: String) {
        builder.addNode(Node.Text(text = beforeLink))
        builder.addNode(Node.Link(link = link))
        builder.addNode(Node.Text(text = afterLink))
    }

    public fun toContent(): RawMessageContent = builder.build()

    public class NodeRegistry
}

public class LinkedText {
    private val stringBuilder: StringBuilder = StringBuilder()

    public operator fun plus(text: String): LinkedText = apply {
        stringBuilder.append(text)
    }

    internal val beforeLink: String get() = stringBuilder.toString().substringBefore("{link}", "")
    internal val afterLink: String get() = stringBuilder.toString().substringAfter("{link}", "")
}

public sealed class Node(
    public open val text: String? = null,
    public open val link: String? = null,
    public open val image: String? = null
) {
    public class Text(override val text: String) : Node(text = text)
    public class Image(override val image: String) : Node(image = image)
    public class Link(override val link: String) : Node(text = link, link = link)
}

public class NodeEncoder {
    public fun encodeNode(node: Node): RawMessageContentNode {
        return RawMessageContentNode(
            documentObject = node.nodeObject,
            type = node.nodeType.optional(),
            data = node.nodeData,
            nodes = listOf(
                RawMessageContentNode(
                    leaves = listOf(
                        RawMessageContentNodeLeaves(
                            leavesObject = "leaf",
                            text = node.text ?: "",
                            marks = emptyList()
                        )
                    ).optional(), documentObject = "text"
                )
            )
        )
    }
}

public val Node.nodeObject: String
    get() = when (this) {
        is Node.Link -> "inline"
        else -> "block"
    }

public val Node.nodeType: String
    get() = when (this) {
        is Node.Text -> "paragraph"
        is Node.Link -> "link"
        is Node.Image -> "image"
    }

public val Node.nodeData: RawMessageContentData
    get() = when (this) {
        is Node.Text -> RawMessageContentData()
        is Node.Link -> RawMessageContentData(href = link.optional())
        is Node.Image -> RawMessageContentData(src = image.optional())
    }
