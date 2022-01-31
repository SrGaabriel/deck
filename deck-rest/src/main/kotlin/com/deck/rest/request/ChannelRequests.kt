package com.deck.rest.request

import com.deck.common.entity.*
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
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

@Serializable
public data class CreateForumThreadRequest(
    val threadId: IntGenericId,
    val title: String,
    val message: RawMessageContent
)

@Serializable
public data class CreateForumThreadReplyRequest(
    val id: IntGenericId,
    val message: RawMessageContent
)

@Serializable
public data class CreateForumThreadReplyResponse(
    val groupId: GenericId,
    val replyId: IntGenericId
)

@Serializable
public data class AddForumThreadReplyReactionRequest(
    val customReactionId: IntGenericId,
    val teamId: GenericId
)

@Serializable
public data class GetChannelMessagesResponse(
    val messages: List<RawMessage>
)

@Serializable
public data class PinMessageRequest(
    val messageId: UniqueId
)