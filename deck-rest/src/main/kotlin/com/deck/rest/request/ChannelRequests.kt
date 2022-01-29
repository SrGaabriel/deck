package com.deck.rest.request

import com.deck.common.entity.RawMessage
import com.deck.common.util.UniqueId
import kotlinx.serialization.Serializable

@Serializable
public data class SendMessageRequest(
    public val content: String,
    public val isPrivate: Boolean = false,
    public val replyMessageIds: List<UniqueId> = emptyList()
)

@Serializable
public data class SendMessageResponse(
    public val message: RawMessage
)

@Serializable
public data class UpdateMessageRequest(
    public val content: String
)

@Serializable
public data class GetChannelMessagesResponse(
    public val messages: List<RawMessage>
)