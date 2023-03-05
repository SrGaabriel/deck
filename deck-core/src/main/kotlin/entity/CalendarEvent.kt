package io.github.srgaabriel.deck.core.entity

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.core.entity.channel.CalendarChannel
import io.github.srgaabriel.deck.core.stateless.StatelessRole
import io.github.srgaabriel.deck.core.stateless.StatelessUser
import io.github.srgaabriel.deck.core.stateless.channel.content.StatelessCalendarEvent
import io.github.srgaabriel.deck.core.stateless.channel.content.StatelessCalendarEventSeries
import io.github.srgaabriel.deck.core.util.BlankStatelessCalendarEventSeries
import io.github.srgaabriel.deck.core.util.BlankStatelessRole
import io.github.srgaabriel.deck.core.util.BlankStatelessUser
import kotlinx.datetime.Instant
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

/**
 * Represents an event scheduled in a [CalendarChannel]
 */
public interface CalendarEvent: StatelessCalendarEvent {
    /** Calendar event's name */
    public val name: String
    /** Calendar event's description, null if none */
    public val description: String?
    /** Calendar event's location, null if not defined */
    public val location: String?
    /** Url associated with calendar event, null if not defined */
    public val url: String?

    /** Max attendance, null if none */
    public val rsvpLimit: Int?
    /** Calendar event's color, null if not defined */
    public val color: Int?
    /** Does the event last all day */
    public val lastsAllDay: Boolean
    /** Does this event repeat */
    public val repeats: Boolean

    /** The id of the series this event belongs to, null if none */
    public val seriesId: UUID?
    /** Wrapper of [seriesId] */
    public val series: StatelessCalendarEventSeries?
        get() = seriesId?.let { BlankStatelessCalendarEventSeries(client, it, channelId, serverId) }

    /** Allowed roles for this event */
    public val roleIds: List<IntGenericId>
    /** Wrapper of [roleIds] */
    public val roles: List<StatelessRole> get() = roleIds.map { BlankStatelessRole(client, id, serverId) }



    /** Calendar event's duration in minutes, null if not defined */
    public val durationInMinutes: Int?
    /** Wrapper of [durationInMinutes] */
    public val duration: Duration? get() = durationInMinutes?.minutes

    /** Calendar event start time */
    public val startsAt: Instant
    /** Whether this calendar event is private */
    public val isPrivate: Boolean

    /** When this calendar event was created */
    public val createdAt: Instant
    /** Who created this calendar event */
    public val createdBy: GenericId

    public val creator: StatelessUser get() = BlankStatelessUser(client, createdBy)

    /** Null if event was not cancelled or the cancellation description was not defined */
    public val cancellationDescription: String?
    /** Null if event was not cancelled */
    public val cancelledBy: GenericId?

    public val canceller: StatelessUser? get() = cancelledBy?.let { BlankStatelessUser(client, it) }

    public val isCancelled: Boolean get() = cancelledBy != null
}