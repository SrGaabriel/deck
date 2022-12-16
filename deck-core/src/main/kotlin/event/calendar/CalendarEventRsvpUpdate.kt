package io.github.srgaabriel.deck.core.event.calendar

import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.CalendarEventRsvp
import io.github.srgaabriel.deck.core.entity.impl.DeckCalendarEventRsvp
import io.github.srgaabriel.deck.core.event.DeckEvent
import io.github.srgaabriel.deck.core.event.EventMapper
import io.github.srgaabriel.deck.core.event.EventService
import io.github.srgaabriel.deck.core.event.mapper
import io.github.srgaabriel.deck.core.stateless.StatelessCalendarEvent
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.channel.StatelessCalendarChannel
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayCalendarEventRsvpUpdatedEvent

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