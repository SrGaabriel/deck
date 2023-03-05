package io.github.srgaabriel.deck.core.event.calendar

import io.github.srgaabriel.deck.common.util.Emote
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.common.util.getValue
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.CalendarEvent
import io.github.srgaabriel.deck.core.event.DeckEvent
import io.github.srgaabriel.deck.core.event.EventMapper
import io.github.srgaabriel.deck.core.event.EventService
import io.github.srgaabriel.deck.core.event.mapper
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.StatelessUser
import io.github.srgaabriel.deck.core.stateless.channel.StatelessMessageChannel
import io.github.srgaabriel.deck.core.stateless.channel.content.StatelessCalendarEvent
import io.github.srgaabriel.deck.core.util.BlankStatelessCalendarEvent
import io.github.srgaabriel.deck.core.util.BlankStatelessMessageChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessServer
import io.github.srgaabriel.deck.core.util.BlankStatelessUser
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayCalendarEventReactionDeletedEvent
import java.util.*

/**
 * Called when someone removes a reaction from a [CalendarEvent]
 * (not only when a reaction emote is removed completely from a topic)
 */
public data class CalendarEventReactionRemoveEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val serverId: GenericId,
    val channelId: UUID,
    val calendarEventId: IntGenericId,
    val userId: GenericId,
    val emote: Emote
): DeckEvent {
    val server: StatelessServer? by lazy { BlankStatelessServer(client, serverId) }
    val channel: StatelessMessageChannel by lazy { BlankStatelessMessageChannel(client, channelId, serverId) }
    val calendarEvent: StatelessCalendarEvent by lazy { BlankStatelessCalendarEvent(client, calendarEventId, channelId, serverId) }
    val user: StatelessUser by lazy { BlankStatelessUser(client, userId) }
}

internal val EventService.calendarEventReactionRemove: EventMapper<GatewayCalendarEventReactionDeletedEvent, CalendarEventReactionRemoveEvent> get() = mapper { client, event ->
    CalendarEventReactionRemoveEvent(
        client = client,
        barebones = event,
        serverId = event.serverId.getValue(),
        channelId = event.reaction.channelId,
        calendarEventId = event.reaction.calendarEventId,
        userId = event.reaction.createdBy,
        emote = Emote.from(event.reaction.emote)
    )
}