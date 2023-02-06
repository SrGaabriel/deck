package io.github.srgaabriel.deck.core.stateless

import io.github.srgaabriel.deck.common.entity.CalendarEventRsvpStatus
import io.github.srgaabriel.deck.common.util.Emote
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.core.entity.CalendarEvent
import io.github.srgaabriel.deck.core.entity.CalendarEventComment
import io.github.srgaabriel.deck.core.entity.CalendarEventRsvp
import io.github.srgaabriel.deck.core.entity.impl.DeckCalendarEvent
import io.github.srgaabriel.deck.core.entity.impl.DeckCalendarEventComment
import io.github.srgaabriel.deck.core.entity.impl.DeckCalendarEventRsvp
import io.github.srgaabriel.deck.core.stateless.channel.StatelessCalendarChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessCalendarChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessServer
import io.github.srgaabriel.deck.core.util.ReactionHolder
import io.github.srgaabriel.deck.rest.builder.UpdateCalendarEventRequestBuilder
import java.util.*

public interface StatelessCalendarEvent: StatelessEntity, ReactionHolder {
    public val id: IntGenericId
    public val channelId: UUID
    public val serverId: GenericId

    public val channel: StatelessCalendarChannel get() = BlankStatelessCalendarChannel(client, channelId, serverId)
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    /**
     * **Patches** this calendar event with the data provided in the [builder]
     *
     * @param builder patch builder
     * @return updated calendar event
     */
    public suspend fun update(builder: UpdateCalendarEventRequestBuilder.() -> Unit): CalendarEvent =
        DeckCalendarEvent.from(client, client.rest.channel.updateCalendarEvent(channelId, id, builder))

    /** Deletes this calendar event */
    public suspend fun delete(): Unit =
        client.rest.channel.deleteCalendarEvent(channelId, id)

    /**
     * Creates a new RSVP or updates the status an already existing one.
     *
     * @param userId the RSVP receiver
     * @param status the RSVP status
     *
     * @return created or updated RSVP
     */
    public suspend fun createOrUpdateRsvp(userId: GenericId, status: CalendarEventRsvpStatus): CalendarEventRsvp =
        DeckCalendarEventRsvp.from(client, client.rest.channel.putCalendarEventRsvp(channelId, id, userId, status))

    /**
     * Searches for an existing RSVP to the provided user.
     *
     * @param userId the RSVP receiver
     *
     * @return found RSVP
     */
    public suspend fun getRsvp(userId: GenericId): CalendarEventRsvp =
        DeckCalendarEventRsvp.from(client, client.rest.channel.getCalendarEventRsvp(channelId, id, userId))

    /**
     * Retrieves all RSVPS in this calendar event
     *
     * @return a list of RSVPS
     */
    public suspend fun getRsvps(): List<CalendarEventRsvp> =
        client.rest.channel.getCalendarEventRsvps(channelId, id).map { DeckCalendarEventRsvp.from(client, it) }

    /**
     * Deletes an RSVP from this calendar event
     *
     * @param userId the RSVP receiver
     */
    public suspend fun deleteRsvp(userId: GenericId): Unit =
        client.rest.channel.deleteCalendarEventRsvp(channelId, id, userId)

    /**
     * Posts a comment under this event
     *
     * @param content comment's content
     *
     * @return the created comment
     */
    public suspend fun createComment(content: String): CalendarEventComment =
        DeckCalendarEventComment.from(client, serverId, client.rest.channel.createCalendarEventComment(channelId, id, content))

    /**
     * Retrieves the comment associated with the provided [commentId]
     *
     * @param commentId comment id
     *
     * @return the found comment
     */
    public suspend fun getComment(commentId: IntGenericId): CalendarEventComment =
        DeckCalendarEventComment.from(client, serverId, client.rest.channel.getCalendarEventComment(channelId, id, commentId))

    /**
     * Retrieves all comments posted under this event
     *
     * @return all comments under this event
     */
    public suspend fun getComments(): List<CalendarEventComment> =
        client.rest.channel.getCalendarEventComments(channelId, id).map { DeckCalendarEventComment.from(client, serverId, it) }

    /**
     * Deletes the comment associated with the provided [commentId]
     *
     * @param commentId comment id
     */
    public suspend fun deleteComment(commentId: IntGenericId): Unit =
        client.rest.channel.deleteCalendarEventComment(channelId, id, commentId)

    override suspend fun addReaction(reactionId: IntGenericId): Unit =
        client.rest.channel.addReactionToCalendarEvent(channelId, id, reactionId)

    override suspend fun removeReaction(reactionId: IntGenericId): Unit =
        client.rest.channel.removeReactionFromCalendarEvent(channelId, id, reactionId)

    public suspend fun addReactionToComment(commentId: IntGenericId, reactionId: IntGenericId): Unit =
        client.rest.channel.addReactionToCalendarEventComment(channelId, id, commentId, reactionId)

    public suspend fun addReactionToComment(commentId: IntGenericId, emote: Emote): Unit =
        addReactionToComment(commentId, emote.id)

    public suspend fun removeReactionFromComment(commentId: IntGenericId, emote: Emote): Unit =
        addReactionToComment(commentId, emote.id)

    public suspend fun removeReactionFromComment(commentId: IntGenericId, reactionId: IntGenericId): Unit =
        client.rest.channel.removeReactionFromCalendarEventComment(channelId, id, commentId, reactionId)

    public suspend fun getCalendarEvent(): CalendarEvent =
        DeckCalendarEvent.from(client, client.rest.channel.getCalendarEvent(channelId, id))
}