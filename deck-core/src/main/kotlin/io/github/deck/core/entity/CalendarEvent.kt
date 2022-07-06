package io.github.deck.core.entity

import io.github.deck.common.util.GenericId
import io.github.deck.core.stateless.StatelessCalendarEvent
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.util.BlankStatelessUser
import kotlinx.datetime.Instant
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

public interface CalendarEvent: StatelessCalendarEvent {
    /** Calendar event's name */
    public val name: String
    /** Calendar event's description, null if none */
    public val description: String?
    /** Calendar event's location, null if not defined */
    public val location: String?
    /** Url associated with calendar event, null if not defined */
    public val url: String?

    /** Calendar event's color, null if not defined */
    public val color: Int?
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