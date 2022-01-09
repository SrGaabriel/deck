package com.deck.rest.request

import com.deck.common.entity.RawMessageContent
import com.deck.common.entity.RawPartialMessage
import com.deck.common.entity.RawSlowmode
import com.deck.common.util.UniqueId
import kotlinx.serialization.Serializable

@Serializable
data class SendMessageRequest(
    val messageId: UniqueId,
    val content: RawMessageContent,
    val repliesTools: List<UniqueId> = emptyList(),
    val isSilent: Boolean?,
    val isPrivate: Boolean?
)

@Serializable
data class SendMessageResponse(
    val message: RawPartialMessage,
    val slowmode: RawSlowmode
)