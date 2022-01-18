package com.deck.core.builder

import com.deck.common.util.ContentBuilder
import com.deck.common.util.ContentWrapper
import com.deck.common.util.mapToModel
import com.deck.rest.builder.RequestBuilder
import com.deck.rest.request.SendMessageRequest
import java.util.*

public class DeckMessageBuilder : RequestBuilder<SendMessageRequest> {
    public var id: UUID = UUID.randomUUID()

    public var contentBuilder: ContentBuilder = ContentBuilder()
    public var content: ContentWrapper = ContentWrapper(contentBuilder)

    public var repliesTo: UUID? = null

    public var isSilent: Boolean = false
    public var isPrivate: Boolean = false

    override fun toRequest(): SendMessageRequest = SendMessageRequest(
        messageId = id.mapToModel(),
        content = contentBuilder.build(),
        repliesToIds = listOfNotNull(repliesTo?.mapToModel()),
        isSilent = isSilent,
        isPrivate = isPrivate
    )
}
