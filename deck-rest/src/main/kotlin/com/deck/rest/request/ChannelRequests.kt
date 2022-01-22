package com.deck.rest.request

import com.deck.common.entity.*
import com.deck.common.util.UniqueId
import kotlinx.serialization.Serializable

@Serializable
public data class SendMessageRequest(
    val messageId: UniqueId,
    val content: RawMessageContent,
    val repliesToIds: List<UniqueId>,
    val isSilent: Boolean,
    val isPrivate: Boolean
)

@Serializable
public data class SendMessageResponse(
    val message: RawPartialSentMessage,
    val slowmode: RawSlowmode
)

@Serializable
public data class GetMessageResponse(
    val metadata: GetMessageMetadata
)

@Serializable
public data class GetMessageMetadata(
    val channel: RawChannel,
    val message: RawMessage
)
