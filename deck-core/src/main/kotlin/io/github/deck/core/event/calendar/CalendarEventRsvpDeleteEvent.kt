package io.github.deck.core.event.calendar

import io.github.deck.common.util.GenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.CalendarEventRsvp
import io.github.deck.core.entity.impl.DeckCalendarEventRsvp
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.gateway.event.Payload
import io.github.deck.gateway.event.type.GatewayCalendarEventRsvpDeletedEvent

public data class CalendarEventRsvpDeleteEvent(
    override val client: DeckClient,
    override val payload: Payload,
    val serverId: GenericId,
    val calendarEventRsvp: CalendarEventRsvp,
): DeckEvent {
    val server: StatelessServer get() = BlankStatelessServer(client, serverId)
}

public val EventService.calendarEventRsvpDeleteEvent: EventMapper<GatewayCalendarEventRsvpDeletedEvent, CalendarEventRsvpDeleteEvent>
    get() = mapper { client, event ->
        CalendarEventRsvpDeleteEvent(
            client = client,
            payload = event.payload,
            serverId = event.serverId,
            calendarEventRsvp = DeckCalendarEventRsvp.from(client, event.calendarEventRsvp)
        )
    }