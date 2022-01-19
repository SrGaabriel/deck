package com.deck.core.builder

import com.deck.common.content.Content
import com.deck.common.content.node.NodeStrategy
import com.deck.common.util.mapToModel
import com.deck.rest.builder.RequestBuilder
import com.deck.rest.request.SendMessageRequest
import java.util.*

public class DeckMessageBuilder : RequestBuilder<SendMessageRequest> {
    public var id: UUID = UUID.randomUUID()

    public var content: Content = Content()

    public var repliesTo: UUID? = null

    public var isSilent: Boolean = false
    public var isPrivate: Boolean = false

    override fun toRequest(): SendMessageRequest = SendMessageRequest(
        messageId = id.mapToModel(),
        content = NodeStrategy.encodeContent(content),
        repliesToIds = listOfNotNull(repliesTo?.mapToModel()),
        isSilent = isSilent,
        isPrivate = isPrivate
    )
}
