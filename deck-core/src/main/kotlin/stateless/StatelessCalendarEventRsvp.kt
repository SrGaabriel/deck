package io.github.srgaabriel.deck.core.stateless

import io.github.srgaabriel.deck.common.entity.CalendarEventRsvpStatus
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.core.entity.CalendarEventRsvp
import io.github.srgaabriel.deck.core.entity.impl.DeckCalendarEventRsvp
import io.github.srgaabriel.deck.core.stateless.channel.StatelessCalendarChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessCalendarChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessCalendarEvent
import io.github.srgaabriel.deck.core.util.BlankStatelessServer
import io.github.srgaabriel.deck.core.util.BlankStatelessUser
import java.util.*

public interface StatelessCalendarEventRsvp: StatelessEntity {
    public val userId: GenericId
    public val serverId: GenericId
    public val channelId: UUID
    public val calendarEventId: IntGenericId

    public val user: StatelessUser get() = BlankStatelessUser(client, userId)
    public val channel: StatelessCalendarChannel get() = BlankStatelessCalendarChannel(client, channelId, serverId)
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)
    public val calendarEvent: StatelessCalendarEvent get() = BlankStatelessCalendarEvent(client, calendarEventId, channelId, serverId)

    /**
     * Sets this RSVP status to [status]. If this RSVP does not exist, it will be created with the provided [status].
     *
     * @param status New RSVP status
     *
     * @return updated RSVP
     */
    public suspend fun update(status: CalendarEventRsvpStatus): CalendarEventRsvp =
        DeckCalendarEventRsvp.from(client, client.rest.channel.putCalendarEventRsvp(channelId, calendarEventId, userId, status))

    /**
     * Deletes this RSVP
     */
    public suspend fun delete(): Unit =
        client.rest.channel.deleteCalendarEventRsvp(channelId, calendarEventId, userId)

    public suspend fun getCalendarEvent(): CalendarEventRsvp =
        DeckCalendarEventRsvp.from(client, client.rest.channel.getCalendarEventRsvp(channelId, calendarEventId, userId))
}