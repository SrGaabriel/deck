package io.github.deck.core.stateless

import io.github.deck.common.entity.CalendarEventRsvpStatus
import io.github.deck.common.util.GenericId
import io.github.deck.common.util.IntGenericId
import io.github.deck.core.entity.CalendarEvent
import io.github.deck.core.entity.CalendarEventRsvp
import io.github.deck.core.entity.impl.DeckCalendarEvent
import io.github.deck.core.entity.impl.DeckCalendarEventRsvp
import io.github.deck.core.stateless.channel.StatelessCalendarChannel
import io.github.deck.core.util.BlankStatelessCalendarChannel
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.rest.builder.UpdateCalendarEventRequestBuilder
import java.util.*

public interface StatelessCalendarEvent: StatelessEntity {
    public val id: IntGenericId
    public val channelId: UUID
    public val serverId: GenericId

    public val channel: StatelessCalendarChannel get() = BlankStatelessCalendarChannel(client, channelId, serverId)
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    /**
     * **Patches** this calendar event with the data provided in the [builder]
     *
     * @param builder patch builder
     * @return updated calendar event
     */
    public suspend fun update(builder: UpdateCalendarEventRequestBuilder.() -> Unit): CalendarEvent =
        DeckCalendarEvent.from(client, client.rest.channel.updateCalendarEvent(channelId, id, builder))

    /** Deletes this calendar event */
    public suspend fun delete(): Unit =
        client.rest.channel.deleteCalendarEvent(channelId, id)

    /**
     * Creates a new RSVP or updates the status an already existing one.
     *
     * @param userId the RSVP receiver
     * @param status the RSVP status
     *
     * @return created or updated RSVP
     */
    public suspend fun createOrUpdateRsvp(userId: GenericId, status: CalendarEventRsvpStatus): CalendarEventRsvp =
        DeckCalendarEventRsvp.from(client, client.rest.channel.putCalendarEventRsvp(channelId, id, userId, status))

    /**
     * Searches for an existing RSVP to the provided user.
     *
     * @param userId the RSVP receiver
     *
     * @return found RSVP
     */
    public suspend fun getRsvp(userId: GenericId): CalendarEventRsvp =
        DeckCalendarEventRsvp.from(client, client.rest.channel.getCalendarEventRsvp(channelId, id, userId))

    /**
     * Retrieves all RSVPS in this calendar event
     *
     * @return a list of RSVPS
     */
    public suspend fun getRsvps(): List<CalendarEventRsvp> =
        client.rest.channel.getCalendarEventRsvps(channelId, id).map { DeckCalendarEventRsvp.from(client, it) }

    /**
     * Deletes an RSVP from this calendar event
     *
     * @param userId the RSVP receiver
     */
    public suspend fun deleteRsvp(userId: GenericId): Unit =
        client.rest.channel.deleteCalendarEventRsvp(channelId, id, userId)

    public suspend fun getCalendarEvent(): CalendarEvent =
        DeckCalendarEvent.from(client, client.rest.channel.getCalendarEvent(channelId, id))
}