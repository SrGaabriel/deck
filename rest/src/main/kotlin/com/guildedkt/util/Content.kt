package com.guildedkt.util

import com.guildedkt.entity.RawMessageContent
import com.guildedkt.entity.RawMessageContentNode
import com.guildedkt.entity.RawMessageContentNodeLeaves

class ContentBuilder {
    var text: String = ""
    var image: String? = null

    fun toContent() = RawMessageContent(
        contentObject = "value",
        document = RawMessageContentNode(
            documentObject = "document",
            nodes = listOf(RawMessageContentNode(
                documentObject = "block",
                type = "paragraph".optional(),
                nodes = listOf(RawMessageContentNode(
                    leaves = listOf(RawMessageContentNodeLeaves(
                        leavesObject = "leaf",
                        text = text
                    )).optional(),
                    documentObject = "text"
                ))
            ))
        )
    )
}
