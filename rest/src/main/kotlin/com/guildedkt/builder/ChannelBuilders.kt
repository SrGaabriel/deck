package com.guildedkt.builder

import com.guildedkt.mapToModel
import com.guildedkt.request.SendMessageRequest
import com.guildedkt.util.ContentBuilder
import java.util.*

class SendMessageRequestBuilder: RequestBuilder<SendMessageRequest> {
    var uniqueId: UUID = UUID.randomUUID()
    var private: Boolean = false
    var silent: Boolean = false

    val content: ContentBuilder = ContentBuilder()

    var text: String
        @Deprecated("Only returns content in the first non-empty node.")
        get() = content.nodes.firstOrNull { it.text != null }?.text ?: ""
        set(value) = content.setText(value)
    var images: List<String>
        get() = content.nodes.mapNotNull { it.image }
        set(value) = content.setImages(value)

    override fun toRequest() = SendMessageRequest(
        messageId = uniqueId.mapToModel(),
        content = content.build(),
        isPrivate = private,
        isSilent = silent
    )
}
