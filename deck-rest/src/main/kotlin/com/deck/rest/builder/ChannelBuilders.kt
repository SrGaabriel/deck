package com.deck.rest.builder

import com.deck.common.util.ContentBuilder
import com.deck.common.util.ContentWrapper
import com.deck.common.util.mapToModel
import com.deck.rest.request.SendMessageRequest
import java.util.*

public class SendMessageRequestBuilder : RequestBuilder<SendMessageRequest> {
    public var uniqueId: UUID = UUID.randomUUID()
    public var private: Boolean = false
    public var silent: Boolean = false

    public var contentBuilder: ContentBuilder = ContentBuilder()
    public val content: ContentWrapper = ContentWrapper(contentBuilder)

    public var repliesTo: UUID? = null

    @Deprecated("Use content instead", ReplaceWith("content"))
    public val messageText: String
        get() = contentBuilder.nodes.firstOrNull { it.text != null }?.text ?: ""

    @Deprecated("Use content instead", ReplaceWith("content"))
    public val messageImages: List<String>
        get() = contentBuilder.nodes.mapNotNull { it.image }

    override fun toRequest(): SendMessageRequest = SendMessageRequest(
        messageId = uniqueId.mapToModel(),
        content = contentBuilder.build(),
        isPrivate = private,
        isSilent = silent,
        repliesToIds = listOfNotNull(repliesTo?.mapToModel())
    )
}
