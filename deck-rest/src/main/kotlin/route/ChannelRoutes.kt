package io.github.srgaabriel.deck.rest.route

import io.github.srgaabriel.deck.common.entity.*
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.rest.RestClient
import io.github.srgaabriel.deck.rest.builder.*
import io.github.srgaabriel.deck.rest.request.*
import io.github.srgaabriel.deck.rest.util.plusIf
import io.github.srgaabriel.deck.rest.util.sendRequest
import io.ktor.http.*
import kotlinx.datetime.Instant
import java.util.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
public class ChannelRoutes(private val client: RestClient) {
    public suspend fun createChannel(builder: CreateChannelRequestBuilder.() -> Unit): RawServerChannel {
        contract {
            callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
        }
        return client.sendRequest<CreateChannelResponse, CreateChannelRequest>(
            endpoint = "/channels",
            method = HttpMethod.Post,
            body = CreateChannelRequestBuilder().apply(builder).toRequest()
        ).channel
    }

    public suspend fun getChannel(channelId: UUID): RawServerChannel = client.sendRequest<CreateChannelResponse>(
        endpoint = "/channels/${channelId}",
        method = HttpMethod.Get,
    ).channel

    public suspend fun deleteChannel(channelId: UUID): Unit = client.sendRequest(
        endpoint = "/channels/${channelId}",
        method = HttpMethod.Delete,
    )

    public suspend fun sendMessage(
        channelId: UUID,
        builder: SendMessageRequestBuilder.() -> Unit
    ): RawMessage {
        contract {
            callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
        }
        return client.sendRequest<SendMessageResponse, SendMessageRequest>(
            endpoint = "/channels/$channelId/messages",
            method = HttpMethod.Post,
            body = SendMessageRequestBuilder().apply(builder).toRequest()
        ).message
    }

    public suspend fun updateMessage(
        channelId: UUID,
        messageId: UUID,
        builder: UpdateMessageRequestBuilder.() -> Unit
    ): RawMessage = client.sendRequest<SendMessageResponse, UpdateMessageRequest>(
        endpoint = "/channels/$channelId/messages/$messageId",
        method = HttpMethod.Put,
        body = UpdateMessageRequestBuilder().apply(builder).toRequest()
    ).message

    public suspend fun deleteMessage(
        channelId: UUID,
        messageId: UUID,
    ): Unit = client.sendRequest(
        endpoint = "/channels/$channelId/messages/$messageId",
        method = HttpMethod.Delete,
    )

    public suspend fun getMessage(
        channelId: UUID,
        messageId: UUID
    ): RawMessage = client.sendRequest<SendMessageResponse, UUID>(
        endpoint = "/channels/$channelId/messages/$messageId",
        method = HttpMethod.Get
    ).message

    public suspend fun getChannelMessages(
        channelId: UUID,
        before: Instant? = null,
        after: Instant? = null,
        limit: Int = 50,
        includePrivate: Boolean = false
    ): List<RawMessage> = client.sendRequest<GetChannelMessagesResponse>(
        endpoint = "/channels/$channelId/messages?includePrivate=$includePrivate"
            .plusIf(before != null) { "&before=$before" }
            .plusIf(after != null) { "&after=$after" }
            .plusIf(limit != 50) { "&limit=$limit" },
        method = HttpMethod.Get,
    ).messages

    public suspend fun addReactionToContent(
        channelId: UUID,
        messageId: UUID,
        emoteId: IntGenericId
    ): Unit = client.sendRequest(
        endpoint = "/channels/$channelId/content/$messageId/emotes/$emoteId",
        method = HttpMethod.Put
    )

    public suspend fun removeReactionFromContent(
        channelId: UUID,
        messageId: UUID,
        emoteId: IntGenericId
    ): Unit = client.sendRequest(
        endpoint = "/channels/$channelId/content/$messageId/emotes/$emoteId",
        method = HttpMethod.Delete
    )

    public suspend fun createDocumentation(
        channelId: UUID,
        builder: CreateDocumentationRequestBuilder.() -> Unit
    ): RawDocumentation {
        contract {
            callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
        }
        return client.sendRequest<CreateDocumentationResponse, CreateDocumentationRequest>(
            endpoint = "/channels/$channelId/docs",
            method = HttpMethod.Post,
            body = CreateDocumentationRequestBuilder().apply(builder).toRequest()
        ).documentation
    }

    public suspend fun getDocumentation(
        channelId: UUID,
        documentationId: IntGenericId
    ): RawDocumentation = client.sendRequest<CreateDocumentationResponse>(
        endpoint = "/channels/$channelId/docs/$documentationId",
        method = HttpMethod.Get
    ).documentation

    public suspend fun getDocumentations(
        channelId: UUID
    ): List<RawDocumentation> = client.sendRequest<GetDocumentationsResponse>(
        endpoint = "/channels/$channelId/docs",
        method = HttpMethod.Get
    ).documentations

    public suspend fun updateDocumentation(
        channelId: UUID,
        documentationId: IntGenericId,
        builder: CreateDocumentationRequestBuilder.() -> Unit
    ): RawDocumentation {
        contract {
            callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
        }
        return client.sendRequest<CreateDocumentationResponse, CreateDocumentationRequest>(
            endpoint = "/channels/$channelId/docs/$documentationId",
            method = HttpMethod.Put,
            body = CreateDocumentationRequestBuilder().apply(builder).toRequest()
        ).documentation
    }

    public suspend fun deleteDocumentation(
        channelId: UUID,
        documentationId: IntGenericId
    ): Unit = client.sendRequest(
        endpoint = "/channels/$channelId/docs/$documentationId",
        method = HttpMethod.Delete
    )

    public suspend fun createListItem(
        channelId: UUID,
        builder: CreateListItemRequestBuilder.() -> Unit
    ): RawListItem {
        contract {
            callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
        }
        return client.sendRequest<CreateListItemResponse, CreateListItemRequest>(
            endpoint = "/channels/$channelId/items",
            method = HttpMethod.Post,
            body = CreateListItemRequestBuilder().apply(builder).toRequest()
        ).listItem
    }

    public suspend fun completeListItem(
        channelId: UUID,
        listItemId: UUID,
    ): Unit = client.sendRequest(
        endpoint = "/channels/${channelId}/items/${listItemId}/complete",
        method = HttpMethod.Post
    )

    public suspend fun uncompleteListItem(
        channelId: UUID,
        listItemId: UUID,
    ): Unit = client.sendRequest(
        endpoint = "/channels/${channelId}/items/${listItemId}/complete",
        method = HttpMethod.Delete
    )

    public suspend fun getListItem(
        channelId: UUID,
        listItemId: UUID,
    ): RawListItem = client.sendRequest<CreateListItemResponse, Unit>(
        endpoint = "/channels/$channelId/items/$listItemId",
        method = HttpMethod.Get
    ).listItem

    public suspend fun getListChannelItems(channelId: UUID): List<RawListItem> = client.sendRequest<GetListChannelItemsResponse, Unit>(
        endpoint = "/channels/$channelId/items",
        method = HttpMethod.Get
    ).listItems

    public suspend fun updateListItem(
        channelId: UUID,
        listItemId: UUID,
        builder: UpdateListItemRequestBuilder.() -> Unit
    ): RawListItem {
        contract {
            callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
        }
        return client.sendRequest<CreateListItemResponse, CreateListItemRequest>(
            endpoint = "/channels/$channelId/items/$listItemId",
            method = HttpMethod.Put,
            body = UpdateListItemRequestBuilder().apply(builder).toRequest()
        ).listItem
    }

    public suspend fun deleteListItem(
        channelId: UUID,
        listItemId: UUID
    ): Unit = client.sendRequest(
        endpoint = "/channels/$channelId/items/$listItemId",
        method = HttpMethod.Delete
    )

    public suspend fun createForumTopic(
        channelId: UUID,
        builder: CreateForumTopicRequestBuilder.() -> Unit
    ): RawForumTopic {
        contract {
            callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
        }
        return client.sendRequest<CreateForumTopicResponse, CreateForumTopicRequest>(
            endpoint = "/channels/$channelId/topics",
            method = HttpMethod.Post,
            body = CreateForumTopicRequestBuilder().apply(builder).toRequest()
        ).forumTopic
    }

    public suspend fun getForumTopic(
        channelId: UUID,
        forumTopicId: IntGenericId
    ): RawForumTopic = client.sendRequest<CreateForumTopicResponse>(
        endpoint = "/channels/${channelId}/topics/${forumTopicId}",
        method = HttpMethod.Get
    ).forumTopic

    public suspend fun getForumTopic(channelId: UUID): List<RawForumTopicSummary> = client.sendRequest<GetForumTopicsResponse>(
        endpoint = "/channels/${channelId}/topics",
        method = HttpMethod.Get
    ).forumTopics

    public suspend fun updateForumTopic(
        channelId: UUID,
        forumTopicId: IntGenericId,
        builder: UpdateForumTopicRequestBuilder.() -> Unit
    ): RawForumTopic {
        contract {
            callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
        }
        return client.sendRequest<CreateForumTopicResponse, UpdateForumTopicRequest>(
            endpoint = "/channels/${channelId}/topics/${forumTopicId}",
            method = HttpMethod.Patch,
            body = UpdateForumTopicRequestBuilder().apply(builder).toRequest()
        ).forumTopic
    }

    public suspend fun lockForumTopic(
        channelId: UUID,
        forumTopicId: IntGenericId
    ): Unit = client.sendRequest(
        endpoint = "/channels/${channelId}/topics/${forumTopicId}/lock",
        method = HttpMethod.Put
    )

    public suspend fun unlockForumTopic(
        channelId: UUID,
        forumTopicId: IntGenericId
    ): Unit = client.sendRequest(
        endpoint = "/channels/${channelId}/topics/${forumTopicId}/lock",
        method = HttpMethod.Delete
    )

    public suspend fun pinForumTopic(
        channelId: UUID,
        forumTopicId: IntGenericId
    ): Unit = client.sendRequest(
        endpoint = "/channels/${channelId}/topics/${forumTopicId}/pin",
        method = HttpMethod.Put
    )

    public suspend fun unpinForumTopic(
        channelId: UUID,
        forumTopicId: IntGenericId
    ): Unit = client.sendRequest(
        endpoint = "/channels/${channelId}/topics/${forumTopicId}/pin",
        method = HttpMethod.Delete
    )

    public suspend fun deleteForumTopic(
        channelId: UUID,
        forumTopicId: IntGenericId
    ): Unit = client.sendRequest(
        endpoint = "/channels/${channelId}/topics/${forumTopicId}",
        method = HttpMethod.Delete
    )

    public suspend fun addReactionToForumTopic(
        channelId: UUID,
        forumTopicId: IntGenericId,
        emoteId: IntGenericId
    ): Unit = client.sendRequest(
        endpoint = "/channels/${channelId}/topics/${forumTopicId}/emotes/${emoteId}",
        method = HttpMethod.Put
    )

    public suspend fun removeReactionFromForumTopic(
        channelId: UUID,
        forumTopicId: IntGenericId,
        emoteId: IntGenericId
    ): Unit = client.sendRequest(
        endpoint = "/channels/${channelId}/topics/${forumTopicId}/emotes/${emoteId}",
        method = HttpMethod.Delete
    )

    public suspend fun createForumTopicComment(
        channelId: UUID,
        forumTopicId: IntGenericId,
        content: String
    ): RawForumTopicComment = client.sendRequest<CreateForumTopicCommentResponse, Map<String, String>>(
        endpoint = "/channels/${channelId}/topics/${forumTopicId}/comments",
        method = HttpMethod.Post,
        body = mapOf("content" to content)
    ).forumTopicComment

    public suspend fun getForumTopicComment(
        channelId: UUID,
        forumTopicId: IntGenericId,
        forumTopicCommentId: IntGenericId
    ): RawForumTopicComment = client.sendRequest<CreateForumTopicCommentResponse>(
        endpoint = "/channels/${channelId}/topics/${forumTopicId}/comments/${forumTopicCommentId}",
        method = HttpMethod.Get
    ).forumTopicComment

    public suspend fun getForumTopicComments(
        channelId: UUID,
        forumTopicId: IntGenericId
    ): List<RawForumTopicComment> = client.sendRequest<GetForumTopicCommentsResponse>(
        endpoint = "/channels/${channelId}/topics/${forumTopicId}/comments",
        method = HttpMethod.Get
    ).forumTopicComments

    public suspend fun updateForumTopicComment(
        channelId: UUID,
        forumTopicId: IntGenericId,
        forumTopicCommentId: IntGenericId,
        content: String
    ): RawForumTopicComment = client.sendRequest<CreateForumTopicCommentResponse, Map<String, String>>(
        endpoint = "/channels/${channelId}/topics/${forumTopicId}/comments/${forumTopicCommentId}",
        method = HttpMethod.Patch,
        body = mapOf("content" to content)
    ).forumTopicComment

    public suspend fun deleteForumTopicComment(
        channelId: UUID,
        forumTopicId: IntGenericId,
        forumTopicCommentId: IntGenericId,
    ): Unit = client.sendRequest(
        endpoint = "/channels/${channelId}/topics/${forumTopicId}/comments/${forumTopicCommentId}",
        method = HttpMethod.Delete
    )

    public suspend fun addReactionToForumTopicComment(
        channelId: UUID,
        forumTopicId: IntGenericId,
        forumTopicCommentId: IntGenericId,
        emoteId: IntGenericId
    ): Unit = client.sendRequest(
        endpoint = "/channels/${channelId}/topics/${forumTopicId}/comments/${forumTopicCommentId}/emotes/${emoteId}",
        method = HttpMethod.Put
    )

    public suspend fun removeReactionFromForumTopicComment(
        channelId: UUID,
        forumTopicId: IntGenericId,
        forumTopicCommentId: IntGenericId,
        emoteId: IntGenericId
    ): Unit = client.sendRequest(
        endpoint = "/channels/${channelId}/topics/${forumTopicId}/comments/${forumTopicCommentId}/emotes/${emoteId}",
        method = HttpMethod.Delete
    )

    public suspend fun createCalendarEvent(
        channelId: UUID,
        builder: CreateCalendarEventRequestBuilder.() -> Unit
    ): RawCalendarEvent {
        contract {
            callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
        }
        return client.sendRequest<CreateCalendarEventResponse, CreateCalendarEventRequest>(
            endpoint = "/channels/${channelId}/events",
            method = HttpMethod.Post,
            body = CreateCalendarEventRequestBuilder().apply(builder).toRequest()
        ).calendarEvent
    }

    public suspend fun getCalendarEvent(
        channelId: UUID,
        calendarEventId: IntGenericId
    ): RawCalendarEvent = client.sendRequest<CreateCalendarEventResponse>(
        endpoint = "/channels/${channelId}/events/${calendarEventId}",
        method = HttpMethod.Get
    ).calendarEvent

    public suspend fun getCalendarEvents(channelId: UUID): List<RawCalendarEvent> = client.sendRequest<GetChannelCalendarEventsResponse>(
        endpoint = "/channels/${channelId}/events",
        method = HttpMethod.Get
    ).calendarEvents

    public suspend fun updateCalendarEvent(
        channelId: UUID,
        calendarEventId: IntGenericId,
        builder: UpdateCalendarEventRequestBuilder.() -> Unit
    ): RawCalendarEvent {
        contract {
            callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
        }
        return client.sendRequest<CreateCalendarEventResponse, UpdateCalendarEventRequest>(
            endpoint = "/channels/${channelId}/events/${calendarEventId}",
            method = HttpMethod.Patch,
            body = UpdateCalendarEventRequestBuilder().apply(builder).toRequest()
        ).calendarEvent
    }

    public suspend fun deleteCalendarEvent(
        channelId: UUID,
        calendarEventId: IntGenericId
    ): Unit = client.sendRequest(
        endpoint = "/channels/${channelId}/events/${calendarEventId}",
        method = HttpMethod.Delete
    )

    public suspend fun addReactionToCalendarEvent(
        channelId: UUID,
        calendarEventId: IntGenericId,
        emoteId: IntGenericId
    ): Unit = client.sendRequest(
        endpoint = "/channels/${channelId}/events/${calendarEventId}/emotes/${emoteId}",
        method = HttpMethod.Put
    )

    public suspend fun removeReactionFromCalendarEvent(
        channelId: UUID,
        calendarEventId: IntGenericId,
        emoteId: IntGenericId
    ): Unit = client.sendRequest(
        endpoint = "/channels/${channelId}/events/${calendarEventId}/emotes/${emoteId}",
        method = HttpMethod.Delete
    )

    public suspend fun createCalendarEventComment(
        channelId: UUID,
        calendarEventId: IntGenericId,
        content: String
    ): RawCalendarEventComment = client.sendRequest<CreateCalendarEventCommentResponse, Map<String, String>>(
        endpoint = "/channels/${channelId}/events/${calendarEventId}/comments",
        method = HttpMethod.Post,
        body = mapOf("content" to content)
    ).calendarEventComment

    public suspend fun getCalendarEventComment(
        channelId: UUID,
        calendarEventId: IntGenericId,
        calendarEventCommentId: IntGenericId
    ): RawCalendarEventComment = client.sendRequest<CreateCalendarEventCommentResponse>(
        endpoint = "/channels/${channelId}/events/${calendarEventId}/comments/${calendarEventCommentId}",
        method = HttpMethod.Get
    ).calendarEventComment

    public suspend fun getCalendarEventComments(
        channelId: UUID,
        calendarEventId: IntGenericId
    ): List<RawCalendarEventComment> = client.sendRequest<GetCalendarEventCommentsResponse>(
        endpoint = "/channels/${channelId}/events/${calendarEventId}/comments",
        method = HttpMethod.Get
    ).calendarEventComments

    public suspend fun updateCalendarEventComment(
        channelId: UUID,
        calendarEventId: IntGenericId,
        calendarEventCommentId: IntGenericId,
        content: String
    ): RawCalendarEventComment = client.sendRequest<CreateCalendarEventCommentResponse, Map<String, String>>(
        endpoint = "/channels/${channelId}/events/${calendarEventId}/comments/${calendarEventCommentId}",
        method = HttpMethod.Patch,
        body = mapOf("content" to content)
    ).calendarEventComment

    public suspend fun deleteCalendarEventComment(
        channelId: UUID,
        calendarEventId: IntGenericId,
        calendarEventCommentId: IntGenericId
    ): Unit = client.sendRequest(
        endpoint = "/channels/${channelId}/events/${calendarEventId}/comments/${calendarEventCommentId}",
        method = HttpMethod.Delete
    )

    public suspend fun addReactionToCalendarEventComment(
        channelId: UUID,
        calendarEventId: IntGenericId,
        calendarEventCommentId: IntGenericId,
        emoteId: IntGenericId
    ): Unit = client.sendRequest(
        endpoint = "/channels/${channelId}/events/${calendarEventId}/comments/${calendarEventCommentId}/emotes/${emoteId}",
        method = HttpMethod.Put
    )

    public suspend fun removeReactionFromCalendarEventComment(
        channelId: UUID,
        calendarEventId: IntGenericId,
        calendarEventCommentId: IntGenericId,
        emoteId: IntGenericId
    ): Unit = client.sendRequest(
        endpoint = "/channels/${channelId}/events/${calendarEventId}/comments/${calendarEventCommentId}/emotes/${emoteId}",
        method = HttpMethod.Delete
    )

    public suspend fun putCalendarEventRsvp(
        channelId: UUID,
        calendarEventId: IntGenericId,
        userId: GenericId,
        status: CalendarEventRsvpStatus,
    ): RawCalendarEventRsvp = client.sendRequest<PutCalendarEventRsvpResponse, PutCalendarEventRsvpRequest>(
        endpoint = "/channels/${channelId}/events/${calendarEventId}/rsvps/${userId}",
        method = HttpMethod.Put,
        body = PutCalendarEventRsvpRequest(status)
    ).calendarEventRsvp

    public suspend fun getCalendarEventRsvp(
        channelId: UUID,
        calendarEventId: IntGenericId,
        userId: GenericId
    ): RawCalendarEventRsvp = client.sendRequest<PutCalendarEventRsvpResponse>(
        endpoint = "/channels/${channelId}/events/${calendarEventId}/rsvps/${userId}",
        method = HttpMethod.Get
    ).calendarEventRsvp

    public suspend fun getCalendarEventRsvps(
        channelId: UUID,
        calendarEventId: IntGenericId
    ): List<RawCalendarEventRsvp> = client.sendRequest<GetCalendarEventRsvpsResponse>(
        endpoint = "/channels/${channelId}/events/${calendarEventId}/rsvps",
        method = HttpMethod.Get
    ).calendarEventRsvps

    public suspend fun deleteCalendarEventRsvp(
        channelId: UUID,
        calendarEventId: IntGenericId,
        userId: GenericId
    ): Unit = client.sendRequest(
        endpoint = "/channels/${channelId}/events/${calendarEventId}/rsvps/${userId}",
        method = HttpMethod.Delete
    )
}