package com.deck.rest.builder

import com.deck.common.util.mapToModel
import com.deck.common.util.nullableOptional
import com.deck.rest.request.CreateDocumentationRequest
import com.deck.rest.request.CreateListItemRequest
import com.deck.rest.request.SendMessageRequest
import java.util.*

public class SendMessageRequestBuilder: RequestBuilder<SendMessageRequest> {
    public var content: String? = null
    public var isPrivate: Boolean = false

    public val repliesTo: MutableList<UUID> = mutableListOf()

    public fun addReplyTarget(vararg messageIds: UUID): Unit =
        repliesTo.addAll(messageIds).let {}

    override fun toRequest(): SendMessageRequest = SendMessageRequest(
        content = content ?: error("Can't send an empty message"),
        isPrivate = isPrivate,
        replyMessageIds = repliesTo.toList().map(UUID::mapToModel)
    )
}

public class CreateDocumentationRequestBuilder: RequestBuilder<CreateDocumentationRequest> {
    public var title: String? = null
    public var content: String? = null

    override fun toRequest(): CreateDocumentationRequest = CreateDocumentationRequest(
        title = title ?: error("Can't create a documentation without a title."),
        content = content ?: error("Can't create an empty documentation.")
    )
}

public class CreateListItemRequestBuilder: RequestBuilder<CreateListItemRequest> {
    public var label: String? = null
    public var note: String? = null

    override fun toRequest(): CreateListItemRequest = CreateListItemRequest(
        message = label ?: error("Can't create an empty list item."),
        note = note.nullableOptional()
    )
}