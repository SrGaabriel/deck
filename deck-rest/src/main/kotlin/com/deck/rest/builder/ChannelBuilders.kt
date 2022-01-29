package com.deck.rest.builder

import com.deck.common.util.mapToModel
import com.deck.rest.request.SendMessageRequest
import java.util.*

public class SendMessageRequestBuilder: RequestBuilder<SendMessageRequest> {
    public var content: String? = null
    public var isPrivate: Boolean = false

    public var repliesTo: List<UUID> = emptyList()

    override fun toRequest(): SendMessageRequest = SendMessageRequest(
        content = content ?: error("Can't send an empty message"),
        isPrivate = isPrivate,
        replyMessageIds = repliesTo.map(UUID::mapToModel)
    )
}