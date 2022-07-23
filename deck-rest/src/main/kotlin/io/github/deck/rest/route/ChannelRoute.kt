package io.github.deck.rest.route

import io.github.deck.common.entity.*
import io.github.deck.common.util.GenericId
import io.github.deck.common.util.IntGenericId
import io.github.deck.rest.RestClient
import io.github.deck.rest.builder.*
import io.github.deck.rest.request.*
import io.github.deck.rest.util.plusIf
import io.github.deck.rest.util.sendRequest
import io.ktor.http.*
import kotlinx.datetime.Instant
import java.util.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
public class ChannelRoute(private val client: RestClient) {
    public suspend fun createChannel(builder: CreateChannelRequestBuilder.() -> Unit): RawServerChannel = client.sendRequest<CreateChannelResponse, CreateChannelRequest>(
        endpoint = "/channels",
        method = HttpMethod.Post,
        body = CreateChannelRequestBuilder().apply(builder).toRequest()
    ).channel

    public suspend fun retrieveChannel(channelId: UUID): RawServerChannel = client.sendRequest<CreateChannelResponse>(
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
    ): RawMessage = client.sendRequest<SendMessageResponse, SendMessageRequest>(
        endpoint = "/channels/$channelId/messages",
        method = HttpMethod.Post,
        body = SendMessageRequestBuilder().apply(builder).toRequest()
    ).message

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
        endpoint = "/channels/$channelId/messages"
            .plusIf(includePrivate) { "?includePrivate=true" }
            .plusIf(before != null) { "?before=$before" }
            .plusIf(after != null) { "?after=$before" }
            .plusIf(limit != 50) { "?limit=$limit" },
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
    ): RawDocumentation = client.sendRequest<CreateDocumentationResponse, CreateDocumentationRequest>(
        endpoint = "/channels/$channelId/docs",
        method = HttpMethod.Post,
        body = CreateDocumentationRequestBuilder().apply(builder).toRequest()
    ).documentation

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
    ): RawDocumentation = client.sendRequest<CreateDocumentationResponse, CreateDocumentationRequest>(
        endpoint = "/channels/$channelId/docs/$documentationId",
        method = HttpMethod.Put,
        body = CreateDocumentationRequestBuilder().apply(builder).toRequest()
    ).documentation

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
    ): RawListItem = client.sendRequest<CreateListItemResponse, CreateListItemRequest>(
        endpoint = "/channels/$channelId/items",
        method = HttpMethod.Post,
        body = CreateListItemRequestBuilder().apply(builder).toRequest()
    ).listItem

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

    public suspend fun retrieveListItem(
        channelId: UUID,
        listItemId: UUID,
    ): RawListItem = client.sendRequest<CreateListItemResponse, Unit>(
        endpoint = "/channels/$channelId/items/$listItemId",
        method = HttpMethod.Get
    ).listItem

    public suspend fun retrieveListChannelItems(channelId: UUID): List<RawListItem> = client.sendRequest<GetListChannelItemsResponse, Unit>(
        endpoint = "/channels/$channelId/items",
        method = HttpMethod.Get
    ).listItems

    public suspend fun updateListItem(
        channelId: UUID,
        listItemId: UUID,
        builder: UpdateListItemRequestBuilder.() -> Unit
    ): RawListItem = client.sendRequest<CreateListItemResponse, CreateListItemRequest>(
        endpoint = "/channels/$channelId/items/$listItemId",
        method = HttpMethod.Put,
        body = UpdateListItemRequestBuilder().apply(builder).toRequest()
    ).listItem

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

    public suspend fun deleteForumTopic(
        channelId: UUID,
        forumTopicId: IntGenericId
    ): Unit = client.sendRequest(
        endpoint = "/channels/${channelId}/topics/${forumTopicId}",
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