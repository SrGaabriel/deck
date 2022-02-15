package com.deck.rest.request

import com.deck.common.entity.*
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.OptionalProperty
import com.deck.common.util.UniqueId
import kotlinx.datetime.Instant
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
public data class CreateTeamChannelRequest(
    val name: String,
    val contentType: RawChannelContentType,
    val isPublic: Boolean,
    val channelCategoryId: OptionalProperty<IntGenericId> = OptionalProperty.NotPresent
)

@Serializable
public data class CreatePrivateChannelRequest(
    val users: List<RawGenericIdObject>
)

@Serializable
public data class CreateChannelResponse(
    val channel: RawChannel
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

@Serializable
public data class CreateScheduleAvailabilityRequest(
    val startDate: Instant,
    val endDate: Instant
)

@Serializable
public data class CreateScheduleAvailabilityResponse(
    val id: IntGenericId,
    val availabilities: List<RawChannelAvailability>
)