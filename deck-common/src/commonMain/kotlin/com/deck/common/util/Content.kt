package com.deck.common.util

import com.deck.common.entity.RawMessageContent
import com.deck.common.entity.RawMessageContentData
import com.deck.common.entity.RawMessageContentNode
import com.deck.common.entity.RawMessageContentNodeLeaves

class ContentBuilder {
    val nodes = mutableListOf<Node>()
    private val nodeEncoder = NodeEncoder()
    /**
     * Simply adds a new node to our list and returning
     * [Unit].
     *
     * @param node The node to be added
     */
    fun addNode(node: Node) = node.let { nodes.add(it); Unit }

    fun build() = RawMessageContent(
        contentObject = "value",
        document = RawMessageContentNode(
            documentObject = "document",
            data = RawMessageContentData(),
            nodes = nodes.map { nodeEncoder.encodeNode(it) }
        )
    )
}

class ContentWrapper(val builder: ContentBuilder = ContentBuilder()) {
    val add = NodeRegistry()

    /**
     * Add a new paragraph node with [text] content skipping
     * a line or a previous node, meaning that you need not provide empty values
     * to skip lines, since each time you call this function you'll be doing exactly that.
     *
     * @param text The written content of the message
     */
    infix fun NodeRegistry.text(text: String) = builder.addNode(Node.Text(text = text))

    /**
     * Adds an image to the message, this method can be called between
     * other nodes, meaning that you can have images anywhere you want in the message,
     * not only at the bottom.
     *
     * @param image The link of the image you'll be sending.
     */
    infix fun NodeRegistry.image(image: String) = builder.addNode(Node.Image(image = image))

    /**
     * Adds a link to the message, it's important because it'll inline/embed
     * your link, if you try to send it just using [text], it won't do anything.
     *
     * @param text The text plus the placeholder {link}, which will be replaced
     * by the specified link in [LinkedText].
     */
    infix fun NodeRegistry.linked(text: String): LinkedText = (LinkedText() + text)

    inline operator fun invoke(scope: ContentWrapper.() -> Unit) =
        this.apply(scope)

    infix fun LinkedText.whereLink(link: String) {
        builder.addNode(Node.Text(text = beforeLink))
        builder.addNode(Node.Link(link = link))
        builder.addNode(Node.Text(text = afterLink))
    }

    fun toContent() = builder.build()

    class NodeRegistry
}

class LinkedText {
    private val stringBuilder = StringBuilder()

    operator fun plus(text: String): LinkedText = apply {
        stringBuilder.append(text)
    }

    internal val beforeLink: String get() = stringBuilder.toString().substringBefore("{link}", "")
    internal val afterLink: String get() = stringBuilder.toString().substringAfter("{link}", "")
}

sealed class Node(open val text: String? = null, open val link: String? = null, open val image: String? = null) {
    class Text(override val text: String): Node(text = text)
    class Image(override val image: String): Node(image = image)
    class Link(override val link: String): Node(text = link, link = link)
}

class NodeEncoder {
    fun encodeNode(node: Node): RawMessageContentNode {
        return RawMessageContentNode(
            documentObject = node.nodeObject,
            type = node.nodeType.optional(),
            data = node.nodeData,
            nodes = listOf(RawMessageContentNode(leaves = listOf(RawMessageContentNodeLeaves(
                leavesObject = "leaf",
                text = node.text ?: "",
                marks = emptyList()
            )
            ).optional(), documentObject = "text"))
        )
    }
}

val Node.nodeObject: String get() = when(this) {
    is Node.Link -> "inline"
    else -> "block"
}

val Node.nodeType: String get() = when(this) {
    is Node.Text -> "paragraph"
    is Node.Link -> "link"
    is Node.Image -> "image"
}

val Node.nodeData: RawMessageContentData get() = when(this) {
    is Node.Text -> RawMessageContentData()
    is Node.Link -> RawMessageContentData(href = link.optional())
    is Node.Image -> RawMessageContentData(src = image.optional())
}