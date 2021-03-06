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
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayCalendarEventCreatedEvent

/**
 * Called when a [CalendarEvent] is created
 */
public data class CalendarEventCreateEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val serverId: GenericId,
    val calendarEvent: CalendarEvent,
): DeckEvent {
    val server: StatelessServer get() = BlankStatelessServer(client, serverId)
    val channel: StatelessCalendarChannel get() = calendarEvent.channel
}

internal val EventService.calendarEventCreateEvent: EventMapper<GatewayCalendarEventCreatedEvent, CalendarEventCreateEvent> get() = mapper { client, event ->
    CalendarEventCreateEvent(
        client = client,
        barebones = event,
        serverId = event.serverId,
        calendarEvent = DeckCalendarEvent.from(client, event.calendarEvent)
    )
}