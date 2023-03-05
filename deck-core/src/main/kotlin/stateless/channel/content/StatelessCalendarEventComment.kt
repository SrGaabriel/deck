package io.github.srgaabriel.deck.core.stateless.channel.content

import io.github.srgaabriel.deck.common.entity.ServerChannelType
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.core.entity.CalendarEventComment
import io.github.srgaabriel.deck.core.entity.impl.DeckCalendarEventComment
import io.github.srgaabriel.deck.core.stateless.StatelessEntity
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.channel.StatelessCalendarChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessCalendarChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessCalendarEvent
import io.github.srgaabriel.deck.core.util.BlankStatelessServer
import io.github.srgaabriel.deck.core.util.ReactionHolder
import java.util.*

public interface StatelessCalendarEventComment: StatelessEntity, ReactionHolder {
    public val id: IntGenericId
    public val calendarEventId: IntGenericId
    public val channelId: UUID
    public val serverId: GenericId

    public val calendarEvent: StatelessCalendarEvent get() = BlankStatelessCalendarEvent(client, calendarEventId, channelId, serverId)
    public val channel: StatelessCalendarChannel get() = BlankStatelessCalendarChannel(client, channelId, serverId)
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    /**
     * Updates this comment with the new [content] provided
     *
     * @param content new content
     *
     * @return updated calendar event comment
     */
    public suspend fun update(content: String): CalendarEventComment =
        DeckCalendarEventComment.from(client, serverId, client.rest.channel.updateCalendarEventComment(channelId, calendarEventId, id, content))

    /**
     * Deletes this comment
     */
    public suspend fun delete(): Unit =
        client.rest.channel.deleteCalendarEventComment(channelId, calendarEventId, id)

    override suspend fun addReaction(reactionId: IntGenericId): Unit =
        client.rest.channel.addReactionToComment(
            channelId,
            ServerChannelType.Calendar,
            calendarEventId,
            id,
            reactionId
        )

    override suspend fun removeReaction(reactionId: IntGenericId): Unit =
        client.rest.channel.removeReactionFromComment(
            channelId,
            ServerChannelType.Calendar,
            calendarEventId,
            id,
            reactionId
        )

    public suspend fun getForumTopicComment(): CalendarEventComment =
        DeckCalendarEventComment.from(client, serverId, client.rest.channel.getCalendarEventComment(channelId, calendarEventId, id))
}