package com.deck.rest.builder

import com.deck.common.util.mapToModel
import com.deck.common.util.nullableOptional
import com.deck.rest.request.CreateDocumentationRequest
import com.deck.rest.request.CreateForumThreadRequest
import com.deck.rest.request.CreateListItemRequest
import com.deck.rest.request.SendMessageRequest
import com.deck.rest.util.required
import java.util.*

public class SendMessageRequestBuilder: RequestBuilder<SendMessageRequest> {
    public var content: String by required()
    public var isPrivate: Boolean = false

    public val repliesTo: MutableList<UUID> = mutableListOf()

    public fun replyTo(vararg messageIds: UUID): Unit =
        repliesTo.addAll(messageIds).let {}

    override fun toRequest(): SendMessageRequest = SendMessageRequest(
        content = content,
        isPrivate = isPrivate,
        replyMessageIds = repliesTo.toList().map(UUID::mapToModel)
    )
}

public class CreateDocumentationRequestBuilder: RequestBuilder<CreateDocumentationRequest> {
    public var title: String by required()
    public var content: String by required()

    override fun toRequest(): CreateDocumentationRequest = CreateDocumentationRequest(
        title = title,
        content = content
    )
}

public class CreateListItemRequestBuilder: RequestBuilder<CreateListItemRequest> {
    public var label: String by required()
    public var note: String by required()

    override fun toRequest(): CreateListItemRequest = CreateListItemRequest(
        message = label,
        note = note.nullableOptional()
    )
}

public class CreateForumThreadRequestBuilder: RequestBuilder<CreateForumThreadRequest> {
    public var title: String by required()
    public var content: String by required()

    override fun toRequest(): CreateForumThreadRequest = CreateForumThreadRequest(
        title = title,
        content = content
    )
}