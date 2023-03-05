@file:UseSerializers(UUIDSerializer::class)

package io.github.srgaabriel.deck.rest.request

import io.github.srgaabriel.deck.common.entity.*
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.common.util.OptionalProperty
import io.github.srgaabriel.deck.common.util.UUIDSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import java.util.*

@Serializable
public data class CreateChannelRequest(
    public val name: OptionalProperty<String> = OptionalProperty.NotPresent,
    public val topic: OptionalProperty<String> = OptionalProperty.NotPresent,
    public val isPublic: Boolean = false,
    public val type: OptionalProperty<ServerChannelType> = OptionalProperty.NotPresent,
    public val serverId: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    public val groupId: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    public val categoryId: OptionalProperty<IntGenericId> = OptionalProperty.NotPresent
)

@Serializable
public data class CreateChannelResponse(
    public val channel: RawServerChannel
)

@Serializable
public data class SendMessageRequest(
    public val content: JsonElement? = null,
    public val embeds: List<RawEmbed> = emptyList(),
    public val isPrivate: Boolean,
    public val isSilent: Boolean,
    public val replyMessageIds: List<UUID> = emptyList(),
)

@Serializable
public data class SendMessageResponse(
    public val message: RawMessage
)

@Serializable
public data class UpdateMessageRequest(
    public val content: JsonElement? = null,
    public val embeds: List<RawEmbed> = emptyList(),
)

@Serializable
public data class GetChannelMessagesResponse(
    public val messages: List<RawMessage>
)

@Serializable
public data class CreateDocumentationRequest(
    public val title: String,
    public val content: String
)

@Serializable
public data class CreateDocumentationResponse(
    @SerialName("doc")
    public val documentation: RawDocumentation
)

@Serializable
public data class GetDocumentationsResponse(
    @SerialName("docs")
    public val documentations: List<RawDocumentation>
)

@Serializable
public data class CreateListItemRequest(
    public val message: String,
    public val note: OptionalProperty<JsonObject> = OptionalProperty.NotPresent
)

@Serializable
public data class CreateListItemResponse(
    public val listItem: RawListItem
)

@Serializable
public data class GetListChannelItemsResponse(
    public val listItems: List<RawListItem>
)

@Serializable
public data class CreateForumTopicRequest(
    val title: String,
    val content: String
)

@Serializable
public data class UpdateForumTopicRequest(
    val title: OptionalProperty<String> = OptionalProperty.NotPresent,
    val content: OptionalProperty<String> = OptionalProperty.NotPresent
)

@Serializable
public data class CreateForumTopicResponse(
    val forumTopic: RawForumTopic
)

@Serializable
public data class GetForumTopicsResponse(
    val forumTopics: List<RawForumTopicSummary>
)

@Serializable
public data class CreateForumTopicCommentResponse(
    val forumTopicComment: RawForumTopicComment
)

@Serializable
public data class GetForumTopicCommentsResponse(
    val forumTopicComments: List<RawForumTopicComment>
)

@Serializable
public data class UpdateCalendarEventRequest(
    val name: OptionalProperty<String> = OptionalProperty.NotPresent,
    val description: OptionalProperty<String> = OptionalProperty.NotPresent,
    val location: OptionalProperty<String> = OptionalProperty.NotPresent,
    val startsAt: OptionalProperty<Instant> = OptionalProperty.NotPresent,
    val url: OptionalProperty<String> = OptionalProperty.NotPresent,
    val color: OptionalProperty<Int> = OptionalProperty.NotPresent,
    val isAllDay: OptionalProperty<Boolean> = OptionalProperty.NotPresent,
    val rsvpLimit: OptionalProperty<Int> = OptionalProperty.NotPresent,
    val duration: OptionalProperty<Int> = OptionalProperty.NotPresent,
    val roleIds: OptionalProperty<List<Int>> = OptionalProperty.NotPresent,
    val isPrivate: Boolean = false,
    val repeatInfo: OptionalProperty<RawCalendarEventRepeatInfo> = OptionalProperty.NotPresent,
)

@Serializable
public data class CreateCalendarEventResponse(
    val calendarEvent: RawCalendarEvent
)

@Serializable
public data class GetChannelCalendarEventsResponse(
    val calendarEvents: List<RawCalendarEvent>
)

@Serializable
public data class PutCalendarEventRsvpRequest(
    val status: CalendarEventRsvpStatus
)

@Serializable
public data class PutCalendarEventRsvpResponse(
    val calendarEventRsvp: RawCalendarEventRsvp
)

@Serializable
public data class GetCalendarEventRsvpsResponse(
    val calendarEventRsvps: List<RawCalendarEventRsvp>
)

@Serializable
public data class CreateCalendarEventCommentResponse(
    val calendarEventComment: RawCalendarEventComment
)

@Serializable
public data class GetCalendarEventCommentsResponse(
    val calendarEventComments: List<RawCalendarEventComment>
)

@Serializable
public data class CreateDocumentationCommentResponse(
    val docComment: RawDocumentationComment
)

@Serializable
public data class GetDocumentationCommentsResponse(
    val docComments: List<RawDocumentationComment>
)