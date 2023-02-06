package io.github.srgaabriel.deck.core.entity.impl

import io.github.srgaabriel.deck.common.entity.RawCalendarEventComment
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.common.util.asNullable
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.CalendarEventComment
import kotlinx.datetime.Instant
import java.util.*

public class DeckCalendarEventComment(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val serverId: GenericId,
    override val channelId: UUID,
    override val calendarEventId: IntGenericId,
    override val content: String,
    override val createdAt: Instant,
    override val authorId: GenericId,
    override val updatedAt: Instant?
): CalendarEventComment {
    public companion object {
        public fun from(client: DeckClient, serverId: GenericId, raw: RawCalendarEventComment): DeckCalendarEventComment = DeckCalendarEventComment(
            client = client,
            id = raw.id,
            serverId = serverId,
            channelId = raw.channelId,
            calendarEventId = raw.calendarEventId,
            content = raw.content,
            createdAt = raw.createdAt,
            authorId = raw.createdBy,
            updatedAt = raw.updatedAt.asNullable()
        )
    }
}