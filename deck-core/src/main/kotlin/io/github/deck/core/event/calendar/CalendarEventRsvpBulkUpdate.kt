package io.github.deck.core.event.calendar

import io.github.deck.common.util.GenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.CalendarEvent
import io.github.deck.core.entity.CalendarEventRsvp
import io.github.deck.core.entity.impl.DeckCalendarEventRsvp
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayCalendarEventRsvpManyUpdatedEvent

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