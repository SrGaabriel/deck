package com.guildedkt.builder

import com.guildedkt.mapToModel
import com.guildedkt.request.SendMessageRequest
import com.guildedkt.util.ContentBuilder
import com.guildedkt.util.ContentWrapper
import java.util.*

class SendMessageRequestBuilder: RequestBuilder<SendMessageRequest> {
    var uniqueId: UUID = UUID.randomUUID()
    var private: Boolean = false
    var silent: Boolean = false

    val content: ContentWrapper = ContentWrapper()
    val contentBuilder: ContentBuilder by content::builder

    @Deprecated("Use content instead", ReplaceWith("content"))
    val messageText: String
        get() = contentBuilder.nodes.firstOrNull { it.text != null }?.text ?: ""
    @Deprecated("Use content instead", ReplaceWith("content"))
    val messageImages: List<String>
        get() = contentBuilder.nodes.mapNotNull { it.image }

    override fun toRequest() = SendMessageRequest(
        messageId = uniqueId.mapToModel(),
        content = contentBuilder.build(),
        isPrivate = private,
        isSilent = silent
    )
}