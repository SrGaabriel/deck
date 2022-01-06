package com.guildedkt.util

import com.guildedkt.entity.RawMessageContent
import com.guildedkt.entity.RawMessageContentData
import com.guildedkt.entity.RawMessageContentNode
import com.guildedkt.entity.RawMessageContentNodeLeaves
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*

class ContentBuilder {
    val nodes = LinkedList<Node>()

    fun addText(text: String) =
        nodes.add(Node(text = text)).let {}

    fun addImage(image: String) =
        nodes.add(Node(image = image)).let {}

    fun setText(text: String) {
        val node = nodes.firstOrNull { it.text != null } ?: Node()
        node.text = text
    }

    fun setImages(images: List<String>) {
        for (image in images) {
            addImage(image)
        }
    }

    private fun buildNodes() = buildList {
        for (mutableNode in nodes) {
            add(RawMessageContentNode(
                documentObject = "block",
                type = (if (mutableNode.text == null) "image" else "paragraph").optional(),
                data = if (mutableNode.image != null) RawMessageContentData(mutableNode.image!!.optional()) else RawMessageContentData(),
                nodes = listOf(RawMessageContentNode(
                    documentObject = "text",
                    leaves = listOf(RawMessageContentNodeLeaves(
                        leavesObject = "leaf",
                        text = mutableNode.text ?: "",
                        marks = emptyList()
                    )).optional()
                ))
            ))
        }
    }

    fun build() = RawMessageContent(
        contentObject = "value",
        document = RawMessageContentNode(
            documentObject = "document",
            nodes = listOf(RawMessageContentNode(
                documentObject = "block",
                type = "paragraph".optional(),
                nodes = buildNodes()
            ))
        )
    ).also { println(Json.encodeToString(it)) }

    operator fun invoke(builder: ContentBuilder.() -> Unit) {
        this.run(builder)
    }
}

data class Node(var text: String? = null, var image: String? = null)
