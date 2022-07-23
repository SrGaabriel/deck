package io.github.deck.core.event.calendar

import io.github.deck.common.util.GenericId
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
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.gateway.event.Payload
import io.github.deck.gateway.event.type.GatewayCalendarEventRsvpUpdatedEvent

/**
 * Called when a single [CalendarEventRsvp] RSVP is updated
 *
 * @see CalendarEventRsvpBulkUpdateEvent
 */
public data class CalendarEventRsvpUpdateEvent(
    override val client: DeckClient,
    override val payload: Payload,
    val serverId: GenericId,
    val calendarEventRsvp: CalendarEventRsvp,
): DeckEvent {
    val server: StatelessServer get() = BlankStatelessServer(client, serverId)
    val channel: StatelessCalendarChannel get() = calendarEventRsvp.channel
    val calendarEvent: StatelessCalendarEvent get() = calendarEventRsvp.calendarEvent
}

internal val EventService.calendarEventRsvpUpdateEvent: EventMapper<GatewayCalendarEventRsvpUpdatedEvent, CalendarEventRsvpUpdateEvent>
    get() = mapper { client, event ->
        CalendarEventRsvpUpdateEvent(
            client = client,
            payload = event.payload,
            serverId = event.serverId,
            calendarEventRsvp = DeckCalendarEventRsvp.from(client, event.calendarEventRsvp)
        )
    }