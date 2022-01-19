package com.deck.core.entity.misc

import com.deck.common.entity.RawMessageContent
import com.deck.common.util.*

public class Content {
    public val builder: ContentBuilder = ContentBuilder()
    public val wrapper: ContentWrapper = ContentWrapper(builder)

    public val text: List<String> get() = builder.nodes.mapNotNull { it.text }
    public val links: List<String> get() = builder.nodes.mapNotNull { it.link }
    public val images: List<String> get() = builder.nodes.mapNotNull { it.image }

    public val textAsString: String get() = text.joinToString("\n")
}

public fun RawMessageContent.mapToContent(): Content = Content().also {
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
