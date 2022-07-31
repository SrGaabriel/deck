package io.github.deck.core.entity.impl

import io.github.deck.common.entity.CalendarEventRsvpStatus
import io.github.deck.common.entity.RawCalendarEventRsvp
import io.github.deck.common.util.GenericId
import io.github.deck.common.util.IntGenericId
import io.github.deck.common.util.asNullable
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.CalendarEventRsvp
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