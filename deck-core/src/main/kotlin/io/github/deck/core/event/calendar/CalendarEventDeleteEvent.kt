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
import io.github.deck.core.stateless.channel.StatelessCalendarChannel
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.gateway.event.Payload
import io.github.deck.gateway.event.type.GatewayCalendarEventDeletedEvent

/**
 * Called when a [CalendarEvent] is deleted
 */
public data class CalendarEventDeleteEvent(
    override val client: DeckClient,
    override val payload: Payload,
    val serverId: GenericId,
    val calendarEvent: CalendarEvent,
): DeckEvent {
    val server: StatelessServer get() = BlankStatelessServer(client, serverId)
    val channel: StatelessCalendarChannel get() = calendarEvent.channel
}

internal val EventService.calendarEventDeleteEvent: EventMapper<GatewayCalendarEventDeletedEvent, CalendarEventDeleteEvent> get() = mapper { client, event ->
    CalendarEventDeleteEvent(
        client = client,
        payload = event.payload,
        serverId = event.serverId,
        calendarEvent = DeckCalendarEvent.from(client, event.calendarEvent)
    )
}