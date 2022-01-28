package com.deck.core.builder

import com.deck.common.content.Content
import com.deck.common.content.node.encode
import com.deck.common.util.mapToModel
import com.deck.rest.builder.MutableContentHolder
import com.deck.rest.builder.RequestBuilder
import com.deck.rest.request.SendMessageRequest
import java.util.*

public class DeckMessageBuilder : RequestBuilder<SendMessageRequest>, MutableContentHolder {
    public var id: UUID = UUID.randomUUID()

    override var content: Content = Content()

    public var repliesTo: UUID? = null

    public var isSilent: Boolean = false
    public var isPrivate: Boolean = false

    override fun toRequest(): SendMessageRequest = SendMessageRequest(
        messageId = id.mapToModel(),
        content = content.encode(),
        repliesToIds = listOfNotNull(repliesTo?.mapToModel()),
        isSilent = isSilent,
        isPrivate = isPrivate
    )
}