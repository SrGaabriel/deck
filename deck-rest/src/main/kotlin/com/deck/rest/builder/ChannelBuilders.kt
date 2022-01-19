package com.deck.rest.builder

import com.deck.common.content.Content
import com.deck.common.content.node.NodeGlobalStrategy
import com.deck.common.util.mapToModel
import com.deck.rest.request.SendMessageRequest
import java.util.*

public class SendMessageRequestBuilder : RequestBuilder<SendMessageRequest> {
    public var uniqueId: UUID = UUID.randomUUID()
    public var private: Boolean = false
    public var silent: Boolean = false

    public val content: Content = Content()

    public var repliesTo: UUID? = null

    override fun toRequest(): SendMessageRequest = SendMessageRequest(
        messageId = uniqueId.mapToModel(),
        content = NodeGlobalStrategy.encodeContent(content),
        isPrivate = private,
        isSilent = silent,
        repliesToIds = listOfNotNull(repliesTo?.mapToModel())
    )
}
