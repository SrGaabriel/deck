package com.deck.core.stateless.channel

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.entity.channel.ScheduleAvailability
import com.deck.core.stateless.StatelessEntity
import com.deck.core.util.BlankStatelessSchedulingChannel
import com.deck.rest.builder.CreateScheduleAvailabilityBuilder
import java.util.*

public interface StatelessScheduleAvailability: StatelessEntity<ScheduleAvailability> {
    public val id: IntGenericId
    public val teamId: GenericId
    public val channelId: UUID

    public val channel: StatelessSchedulingChannel get() = BlankStatelessSchedulingChannel(client, channelId, teamId)

    /**
     * Updates this availability, requiring a [CreateScheduleAvailabilityBuilder.start] date and an
     * [CreateScheduleAvailabilityBuilder.ending] date too.
     *
     * @param builder availability builder
     *
     * @return list of new updated availabilities
     */
    public suspend fun update(builder: CreateScheduleAvailabilityBuilder.() -> Unit): List<ScheduleAvailability> =
        client.rest.channelRoute.updateAvailability(channelId, id, builder).second.map(client.entityDecoder::decodeScheduleAvailability)

    public suspend fun delete(): Unit =
        client.rest.channelRoute.deleteAvailability(channelId, id)

    override suspend fun getState(): ScheduleAvailability =
        client.entityDelegator.getSchedulingChannelAvailability(id, channel.id)
            ?: error("Tried to access the state of an invalid schedule availability")
}