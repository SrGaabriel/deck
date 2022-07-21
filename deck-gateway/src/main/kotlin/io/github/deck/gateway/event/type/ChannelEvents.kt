package io.github.deck.gateway.event.type

import io.github.deck.common.entity.*
import io.github.deck.common.util.GenericId
import io.github.deck.gateway.event.GatewayEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("TeamChannelCreated")
public data class GatewayServerChannelCreatedEvent(
    val serverId: GenericId,
    val channel: RawServerChannel
): GatewayEvent()

@Serializable
@SerialName("TeamChannelUpdated")
public data class GatewayServerChannelUpdatedEvent(
    val serverId: GenericId,
    val channel: RawServerChannel
): GatewayEvent()

@Serializable
@SerialName("TeamChannelDeleted")
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