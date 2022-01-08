package com.deck.rest.builder

import com.deck.common.util.ContentBuilder
import com.deck.common.util.ContentWrapper
import com.deck.common.util.UniqueId
import com.deck.common.util.mapToModel
import com.deck.rest.request.SendMessageRequest
import java.util.*

class SendMessageRequestBuilder: RequestBuilder<SendMessageRequest> {
    var uniqueId: UniqueId = UUID.randomUUID().mapToModel()
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
        messageId = uniqueId,
        content = contentBuilder.build(),
        isPrivate = private,
        isSilent = silent
    )
}