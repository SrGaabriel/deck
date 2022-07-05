package io.github.deck.core.stateless

import io.github.deck.common.util.GenericId
import io.github.deck.common.util.IntGenericId
import io.github.deck.core.entity.CalendarEvent
import io.github.deck.core.entity.impl.DeckCalendarEvent
import io.github.deck.core.stateless.channel.StatelessCalendarChannel
import io.github.deck.core.util.BlankStatelessCalendarChannel
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.rest.builder.UpdateCalendarEventRequestBuilder
import java.util.*

public interface StatelessCalendarEvent: StatelessEntity {
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
}