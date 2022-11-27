package io.github.deck.core.event.calendar

import io.github.deck.core.DeckClient
import io.github.deck.core.entity.CalendarEventRsvp
import io.github.deck.core.entity.impl.DeckCalendarEventRsvp
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessCalendarEvent
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.stateless.channel.StatelessCalendarChannel
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayCalendarEventRsvpUpdatedEvent

/**
 * Called when a single [CalendarEventRsvp] RSVP is updated
 *
 * @see CalendarEventRsvpBulkUpdateEvent
 */
public data class CalendarEventRsvpUpdateEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val calendarEventRsvp: CalendarEventRsvp,
): DeckEvent {
    val server: StatelessServer by lazy { calendarEventRsvp.server }
    val channel: StatelessCalendarChannel by lazy { calendarEventRsvp.channel }
    val calendarEvent: StatelessCalendarEvent by lazy { calendarEventRsvp.calendarEvent }
}

internal val EventService.calendarEventRsvpUpdate: EventMapper<GatewayCalendarEventRsvpUpdatedEvent, CalendarEventRsvpUpdateEvent>
    get() = mapper { client, event ->
        CalendarEventRsvpUpdateEvent(
            client = client,
            barebones = event,
            calendarEventRsvp = DeckCalendarEventRsvp.from(client, event.calendarEventRsvp)
        )
    }