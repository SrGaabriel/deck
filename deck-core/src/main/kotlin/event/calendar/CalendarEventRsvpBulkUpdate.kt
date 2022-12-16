package io.github.srgaabriel.deck.core.event.calendar

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.CalendarEvent
import io.github.srgaabriel.deck.core.entity.CalendarEventRsvp
import io.github.srgaabriel.deck.core.entity.impl.DeckCalendarEventRsvp
import io.github.srgaabriel.deck.core.event.DeckEvent
import io.github.srgaabriel.deck.core.event.EventMapper
import io.github.srgaabriel.deck.core.event.EventService
import io.github.srgaabriel.deck.core.event.mapper
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.util.BlankStatelessServer
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayCalendarEventRsvpManyUpdatedEvent

/**
 * Called when multiple RSVPs are updated/created at the same time in a single [CalendarEvent]
 */
public data class CalendarEventRsvpBulkUpdateEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val serverId: GenericId,
    val calendarEventRsvps: List<CalendarEventRsvp>,
): DeckEvent {
    val server: StatelessServer get() = BlankStatelessServer(client, serverId)
}

internal val EventService.calendarEventRsvpBulkUpdate: EventMapper<GatewayCalendarEventRsvpManyUpdatedEvent, CalendarEventRsvpBulkUpdateEvent>
    get() = mapper { client, event ->
        CalendarEventRsvpBulkUpdateEvent(
            client = client,
            barebones = event,
            serverId = event.serverId,
            calendarEventRsvps = event.calendarEventRsvps.map { DeckCalendarEventRsvp.from(client, it) }
        )
    }