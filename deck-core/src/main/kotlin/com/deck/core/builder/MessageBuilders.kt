package com.deck.core.builder

import com.deck.common.util.ContentBuilder
import com.deck.common.util.ContentWrapper
import com.deck.common.util.mapToModel
import com.deck.rest.builder.RequestBuilder
import com.deck.rest.request.SendMessageRequest
import java.util.*

class DeckMessageBuilder: RequestBuilder<SendMessageRequest> {
    var id: UUID = UUID.randomUUID()

    var contentBuilder = ContentBuilder()
    var content = ContentWrapper(contentBuilder)

    var repliesTo: UUID? = null

    var isSilent = false
    var isPrivate = false

    override fun toRequest(): SendMessageRequest = SendMessageRequest(
        messageId = id.mapToModel(),
        content = contentBuilder.build(),
        repliesToIds = listOfNotNull(repliesTo?.mapToModel()),
        isSilent = isSilent,
        isPrivate = isPrivate
    )
}
