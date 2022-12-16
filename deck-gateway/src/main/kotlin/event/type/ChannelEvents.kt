package io.github.srgaabriel.deck.gateway.event.type

import io.github.srgaabriel.deck.common.entity.*
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("ServerChannelCreated")
public data class GatewayServerChannelCreatedEvent(
    val serverId: GenericId,
    val channel: RawServerChannel
): GatewayEvent()

@Serializable
@SerialName("ServerChannelUpdated")
public data class GatewayServerChannelUpdatedEvent(
    val serverId: GenericId,
    val channel: RawServerChannel
): GatewayEvent()

@Serializable
@SerialName("ServerChannelDeleted")
public data class GatewayServerChannelDeletedEvent(
    val serverId: GenericId,
    val channel: RawServerChannel
): GatewayEvent()

@Serializable
@SerialName("ListItemCompleted")
public data class GatewayListItemCompletedEvent(
    val serverId: GenericId,
    val listItem: RawListItem
): GatewayEvent()

@Serializable
@SerialName("ListItemCreated")
public data class GatewayListItemCreatedEvent(
    val serverId: GenericId,
    val listItem: RawListItem
): GatewayEvent()

@Serializable
@SerialName("ListItemUpdated")
public data class GatewayListItemUpdatedEvent(
    val serverId: GenericId,
    val listItem: RawListItem
): GatewayEvent()

@Serializable
@SerialName("ListItemDeleted")
public data class GatewayListItemDeletedEvent(
    val serverId: GenericId,
    val listItem: RawListItem
): GatewayEvent()

@Serializable
@SerialName("ListItemUncompleted")
public data class GatewayListItemUncompletedEvent(
    val serverId: GenericId,
    val listItem: RawListItem
): GatewayEvent()

@Serializable
@SerialName("DocCreated")
public data class GatewayDocumentationCreatedEvent(
    val serverId: GenericId,
    @SerialName("doc")
    val documentation: RawDocumentation
): GatewayEvent()

@Serializable
@SerialName("DocUpdateed")
public data class GatewayDocumentationUpdatedEvent(
    val serverId: GenericId,
    @SerialName("doc")
    val documentation: RawDocumentation
): GatewayEvent()

@Serializable
@SerialName("DocDeleted")
public data class GatewayDocumentationDeletedEvent(
    val serverId: GenericId,
    @SerialName("doc")
    val documentation: RawDocumentation
): GatewayEvent()

@Serializable
@SerialName("CalendarEventCreated")
public data class GatewayCalendarEventCreatedEvent(
    val serverId: GenericId,
    val calendarEvent: RawCalendarEvent
): GatewayEvent()

@Serializable
@SerialName("CalendarEventUpdated")
public data class GatewayCalendarEventUpdatedEvent(
    val serverId: GenericId,
    val calendarEvent: RawCalendarEvent
): GatewayEvent()

@Serializable
@SerialName("CalendarEventDeleted")
public data class GatewayCalendarEventDeletedEvent(
    val serverId: GenericId,
    val calendarEvent: RawCalendarEvent
): GatewayEvent()

@Serializable
@SerialName("CalendarEventRsvpUpdated")
public data class GatewayCalendarEventRsvpUpdatedEvent(
    val serverId: GenericId,
    val calendarEventRsvp: RawCalendarEventRsvp
): GatewayEvent()

@Serializable
@SerialName("CalendarEventRsvpManyUpdated")
public data class GatewayCalendarEventRsvpManyUpdatedEvent(
    val serverId: GenericId,
    val calendarEventRsvps: List<RawCalendarEventRsvp>
): GatewayEvent()

@Serializable
@SerialName("CalendarEventRsvpDeleted")
public data class GatewayCalendarEventRsvpDeletedEvent(
    val serverId: GenericId,
    val calendarEventRsvp: RawCalendarEventRsvp
): GatewayEvent()

@Serializable
@SerialName("ForumTopicCreated")
public data class GatewayForumTopicCreatedEvent(
    val serverId: GenericId,
    val forumTopic: RawForumTopic
): GatewayEvent()

@Serializable
@SerialName("ForumTopicUpdated")
public data class GatewayForumTopicUpdatedEvent(
    val serverId: GenericId,
    val forumTopic: RawForumTopic
): GatewayEvent()

@Serializable
@SerialName("ForumTopicLocked")
public data class GatewayForumTopicLockedEvent(
    val serverId: GenericId,
    val forumTopic: RawForumTopic
): GatewayEvent()

@Serializable
@SerialName("ForumTopicUnlocked")
public data class GatewayForumTopicUnlockedEvent(
    val serverId: GenericId,
    val forumTopic: RawForumTopic
): GatewayEvent()

@Serializable
@SerialName("ForumTopicDeleted")
public data class GatewayForumTopicDeletedEvent(
    val serverId: GenericId,
    val forumTopic: RawForumTopic
): GatewayEvent()

@Serializable
@SerialName("ForumTopicPinned")
public data class GatewayForumTopicPinnedEvent(
    val serverId: GenericId,
    val forumTopic: RawForumTopic
): GatewayEvent()

@Serializable
@SerialName("ForumTopicUnpinned")
public data class GatewayForumTopicUnpinnedEvent(
    val serverId: GenericId,
    val forumTopic: RawForumTopic
): GatewayEvent()

@Serializable
@SerialName("ForumTopicCommentCreated")
public data class GatewayForumTopicCommentCreatedEvent(
    val serverId: GenericId,
    val forumTopicComment: RawForumTopicComment
): GatewayEvent()

@Serializable
@SerialName("ForumTopicCommentUpdated")
public data class GatewayForumTopicCommentUpdatedEvent(
    val serverId: GenericId,
    val forumTopicComment: RawForumTopicComment
): GatewayEvent()

@Serializable
@SerialName("ForumTopicCommentDeleted")
public data class GatewayForumTopicCommentDeletedEvent(
    val serverId: GenericId,
    val forumTopicComment: RawForumTopicComment
): GatewayEvent()