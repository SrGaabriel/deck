package com.deck.core.entity.misc

import com.deck.common.entity.RawMessageContent
import com.deck.common.util.*

class Content {
    val builder: ContentBuilder = ContentBuilder()
    val wrapper: ContentWrapper = ContentWrapper(builder)

    val text get() = builder.nodes.mapNotNull { it.text }
    val links get() = builder.nodes.mapNotNull { it.link }
    val images get() = builder.nodes.mapNotNull { it.image }

    val textAsString get() = text.joinToString("\n")
}

fun RawMessageContent.mapToContent() = Content().also {
    val mainNodes = document.nodes.getOrNull(0)?.nodes ?: return Content()
    for (node in mainNodes) {
        val leaf = node.leaves.mapNotNull { leaves -> leaves.getOrNull(0) }.asNullable()
        val wrappedNode = when (node.documentObject) {
            "text" -> Node.Text(text = leaf!!.text)
            "inline" -> Node.Link(link = node.data.href.asNullable()!!)
            "block" -> Node.Image(image = node.data.src.asNullable()!!)
            else -> null
        } ?: continue
        it.builder.addNode(wrappedNode)
    }
}
