package io.github.deck.core.stateless.channel

import io.github.deck.common.util.IntGenericId
import io.github.deck.core.entity.CalendarEvent
import io.github.deck.core.entity.channel.CalendarChannel
import io.github.deck.core.entity.impl.DeckCalendarEvent
import io.github.deck.core.util.getChannelOf
import io.github.deck.rest.builder.CreateCalendarEventRequestBuilder
import io.github.deck.rest.builder.UpdateCalendarEventRequestBuilder

public interface StatelessCalendarChannel: StatelessServerChannel {
    /**
     * Creates a [CalendarEvent] within this calendar channel.
     *
     * @param builder calendar event builder
     * @return the created calendar event
     */
    public suspend fun createCalendarEvent(builder: CreateCalendarEventRequestBuilder.() -> Unit): CalendarEvent =
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

    override suspend fun getChannel(): CalendarChannel =
        client.getChannelOf(id)
}