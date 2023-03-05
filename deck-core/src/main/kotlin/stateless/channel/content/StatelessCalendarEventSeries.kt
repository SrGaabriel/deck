package io.github.srgaabriel.deck.core.stateless.channel.content

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.core.stateless.StatelessEntity
import io.github.srgaabriel.deck.core.stateless.channel.StatelessCalendarChannel
import io.github.srgaabriel.deck.core.util.BlankStatelessCalendarChannel
import io.github.srgaabriel.deck.rest.builder.UpdateCalendarEventRequestBuilder
import java.util.*

public interface StatelessCalendarEventSeries: StatelessEntity {
    public val id: UUID
    public val serverId: GenericId
    public val channelId: UUID

    public val channel: StatelessCalendarChannel get() = BlankStatelessCalendarChannel(client, id, serverId)

    /**
     * **Patches** all events within this series with the data provided in the [builder]
     *
     * @param builder patch builder
     */
    public suspend fun update(builder: UpdateCalendarEventRequestBuilder.() -> Unit): Unit =
        client.rest.channel.updateCalendarEventSeries(channelId, id, builder)

    /** Deletes this series and all events within it */
    public suspend fun delete(): Unit =
        client.rest.channel.deleteCalendarEventSeries(channelId, id)
}