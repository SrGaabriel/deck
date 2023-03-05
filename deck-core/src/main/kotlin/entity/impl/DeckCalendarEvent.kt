package io.github.srgaabriel.deck.core.entity.impl

import io.github.srgaabriel.deck.common.entity.RawCalendarEvent
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.common.util.asNullable
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.CalendarEvent
import kotlinx.datetime.Instant
import java.util.*

public data class DeckCalendarEvent(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val channelId: UUID,
    override val serverId: GenericId,
    override val name: String,
    override val description: String?,
    override val location: String?,
    override val url: String?,
    override val rsvpLimit: Int?,
    override val color: Int?,
    override val seriesId: UUID?,
    override val lastsAllDay: Boolean,
    override val roleIds: List<IntGenericId>,
    override val repeats: Boolean,
    override val durationInMinutes: Int?,
    override val startsAt: Instant,
    override val isPrivate: Boolean,
    override val createdAt: Instant,
    override val createdBy: GenericId,
    override val cancellationDescription: String?,
    override val cancelledBy: GenericId?,
): CalendarEvent {
    public companion object {
        public fun from(client: DeckClient, raw: RawCalendarEvent): DeckCalendarEvent = DeckCalendarEvent(
            client = client,
            id = raw.id,
            channelId = raw.channelId,
            serverId = raw.serverId,
            name = raw.name,
            description = raw.description.asNullable(),
            location = raw.location.asNullable(),
            url = raw.url.asNullable(),
            rsvpLimit = raw.rsvpLimit.asNullable(),
            color = raw.color.asNullable(),
            seriesId = raw.seriesId.asNullable(),
            lastsAllDay = raw.isAllDay,
            roleIds = raw.roleIds.asNullable().orEmpty(),
            repeats = raw.repeats,
            durationInMinutes = raw.duration.asNullable(),
            startsAt = raw.startsAt,
            isPrivate = raw.isPrivate.asNullable() == true,
            createdAt = raw.createdAt,
            createdBy = raw.createdBy,
            cancellationDescription = raw.cancellation.asNullable()?.description?.asNullable(),
            cancelledBy = raw.cancellation.asNullable()?.createdBy
        )
    }
}