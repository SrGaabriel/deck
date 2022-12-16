package io.github.srgaabriel.deck.core.entity.impl

import io.github.srgaabriel.deck.common.entity.CalendarEventRsvpStatus
import io.github.srgaabriel.deck.common.entity.RawCalendarEventRsvp
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.common.util.asNullable
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.CalendarEventRsvp
import kotlinx.datetime.Instant
import java.util.*

public class DeckCalendarEventRsvp(
    override val client: DeckClient,
    override val userId: GenericId,
    override val calendarEventId: IntGenericId,
    override val channelId: UUID,
    override val serverId: GenericId,
    override val status: CalendarEventRsvpStatus,
    override val creatorId: GenericId,
    override val editorId: GenericId?,
    override val createdAt: Instant,
    override val updatedAt: Instant?
): CalendarEventRsvp {
    public companion object {
        public fun from(client: DeckClient, raw: RawCalendarEventRsvp): DeckCalendarEventRsvp =
            DeckCalendarEventRsvp(
                client = client,
                userId = raw.userId,
                calendarEventId = raw.calendarEventId,
                channelId = raw.channelId,
                serverId = raw.serverId,
                status = raw.status,
                creatorId = raw.createdBy,
                editorId = raw.updatedBy.asNullable(),
                createdAt = raw.createdAt,
                updatedAt = raw.updatedAt.asNullable()
            )
    }
}