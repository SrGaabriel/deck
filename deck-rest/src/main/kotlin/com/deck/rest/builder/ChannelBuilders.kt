package com.deck.rest.builder

import com.deck.common.util.ContentBuilder
import com.deck.common.util.ContentWrapper
import com.deck.common.util.mapToModel
import com.deck.rest.request.SendMessageRequest
import java.util.*

class SendMessageRequestBuilder: RequestBuilder<SendMessageRequest> {
    var uniqueId: UUID = UUID.randomUUID()
    var private: Boolean = false
    var silent: Boolean = false

    var contentBuilder: ContentBuilder = ContentBuilder()
    val content: ContentWrapper = ContentWrapper(contentBuilder)

    var repliesTo: UUID? = null

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
        isSilent = silent,
        repliesToIds = listOfNotNull(repliesTo?.mapToModel())
    )
}
