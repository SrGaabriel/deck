package io.github.srgaabriel.deck.core.stateless.channel

import io.github.srgaabriel.deck.common.entity.ServerChannelType
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.core.entity.CalendarEvent
import io.github.srgaabriel.deck.core.entity.channel.CalendarChannel
import io.github.srgaabriel.deck.core.entity.impl.DeckCalendarEvent
import io.github.srgaabriel.deck.rest.builder.UpdateCalendarEventRequestBuilder
import java.util.*

public interface StatelessCalendarChannel: StatelessServerChannel {
    override val type: ServerChannelType get() = ServerChannelType.Calendar

    /**
     * Creates a [CalendarEvent] within this calendar channel.
     *
     * @param builder calendar event builder
     * @return the created calendar event
     */
    public suspend fun createCalendarEvent(builder: UpdateCalendarEventRequestBuilder.() -> Unit): CalendarEvent =
        DeckCalendarEvent.from(client, client.rest.channel.createCalendarEvent(id, builder))

    /**
     * Retrieves the [CalendarEvent] associated with the specified [calendarEventId]
     *
     * @param calendarEventId calendar event id
     * @return found calendar event
     */
    public suspend fun getCalendarEvent(calendarEventId: IntGenericId): CalendarEvent =
        DeckCalendarEvent.from(client, client.rest.channel.getCalendarEvent(id, calendarEventId))

    /**
     * Retrieves all [CalendarEvent]s within this calendar channel
     *
     * @return list of [CalendarEvent]
     */
    public suspend fun getCalendarEvents(): List<CalendarEvent> =
        client.rest.channel.getCalendarEvents(id).map { DeckCalendarEvent.from(client, it) }

    /**
     * **Patches** the calendar event associated with the provided [calendarEventId]
     *
     * @param calendarEventId calendar event's id
     * @param builder update builder
     *
     * @return updated calendar event
     */
    public suspend fun updateCalendarEvent(calendarEventId: IntGenericId, builder: UpdateCalendarEventRequestBuilder.() -> Unit): CalendarEvent =
        DeckCalendarEvent.from(client, client.rest.channel.updateCalendarEvent(id, calendarEventId, builder))

    /**
     * Deletes the calendar event associated with the provided [calendarEventId]
     *
     * @param calendarEventId calendar event's id
     */
    public suspend fun deleteCalendarEvent(calendarEventId: IntGenericId): Unit =
        client.rest.channel.deleteCalendarEvent(id, calendarEventId)

    /**
     * **Patches** the events within the series associated with the provided [calendarEventSeriesId]
     *
     * @param calendarEventSeriesId calendar event series id
     * @param builder update builder
     */
    public suspend fun updateCalendarEventSeries(calendarEventSeriesId: UUID, builder: UpdateCalendarEventRequestBuilder.() -> Unit): Unit =
        client.rest.channel.updateCalendarEventSeries(id, calendarEventSeriesId, builder)

    /**
     * Deletes the calendar event series associated with the provided [calendarEventSeriesId] and all events within it
     *
     * @param calendarEventSeriesId calendar event series id
     */
    public suspend fun deleteCalendarEvent(calendarEventSeriesId: UUID): Unit =
        client.rest.channel.deleteCalendarEventSeries(id, calendarEventSeriesId)

    override suspend fun getChannel(): CalendarChannel =
        client.getChannelOf(id)
}