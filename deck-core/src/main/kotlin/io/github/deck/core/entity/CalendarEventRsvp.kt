package io.github.deck.core.entity

import io.github.deck.common.entity.CalendarEventRsvpStatus
import io.github.deck.common.util.GenericId
import io.github.deck.core.stateless.StatelessCalendarEventRsvp
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.util.BlankStatelessUser
import kotlinx.datetime.Instant

/**
 * Represents an RSVP (invite) to a [CalendarEvent]
 */
public interface CalendarEventRsvp: StatelessCalendarEventRsvp {
    /** RSVP status */
    public val status: CalendarEventRsvpStatus

    /** The RSVP author/sender/creator **(NOT THE RECEIVER)** */
    public val creatorId: GenericId
    public val creator: StatelessUser get() = BlankStatelessUser(client, creatorId)

    /** The last person to edit this RSVP */
    public val editorId: GenericId?
    public val editor: StatelessUser? get() = editorId?.let { BlankStatelessUser(client, it) }

    /** Time of creation of this RSVP */
    public val createdAt: Instant
    /** Time of the last update of this RSVP */
    public val updatedAt: Instant?
}