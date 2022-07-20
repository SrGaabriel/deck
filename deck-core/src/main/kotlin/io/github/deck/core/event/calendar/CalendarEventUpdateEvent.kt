package io.github.deck.core.event.calendar

import io.github.deck.common.util.GenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.CalendarEvent
import io.github.deck.core.entity.impl.DeckCalendarEvent
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.gateway.event.Payload
import io.github.deck.gateway.event.type.GatewayCalendarEventUpdatedEvent

public data class CalendarEventUpdateEvent(
    override val client: DeckClient,
    override val payload: Payload,
    val serverId: GenericId,
    val calendarEvent: CalendarEvent,
): DeckEvent {
    val server: StatelessServer get() = BlankStatelessServer(client, serverId)
}

public val EventService.calendarEventUpdateEvent: EventMapper<GatewayCalendarEventUpdatedEvent, CalendarEventUpdateEvent> get() = mapper { client, event ->
    CalendarEventUpdateEvent(
        client = client,
        payload = event.payload,
        serverId = event.serverId,
        calendarEvent = DeckCalendarEvent.from(client, event.calendarEvent)
    )
}