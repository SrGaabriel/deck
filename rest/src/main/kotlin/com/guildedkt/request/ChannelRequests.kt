package com.guildedkt.request

import com.guildedkt.entity.RawMessageContent
import com.guildedkt.entity.RawPartialMessage
import com.guildedkt.entity.RawSlowmode
import com.guildedkt.util.UniqueId
import kotlinx.serialization.Serializable

@Serializable
data class SendMessageRequest(
    val messageId: UniqueId,
    val content: RawMessageContent,
    val repliesTools: List<UniqueId> = emptyList(),
    val isSilent: Boolean?,
    val isPrivate: Boolean?
)

// wtf guilded
@Serializable
data class SendMessageResponse(
    val message: RawPartialMessage,
    val slowmode: RawSlowmode
)
