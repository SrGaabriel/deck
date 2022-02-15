package com.deck.core.stateless.channel

import com.deck.common.util.IntGenericId
import com.deck.core.entity.channel.ScheduleAvailability
import com.deck.core.stateless.StatelessEntity
import com.deck.core.stateless.StatelessTeam
import com.deck.rest.builder.CreateScheduleAvailabilityBuilder

public interface StatelessScheduleAvailability: StatelessEntity<ScheduleAvailability> {
    public val id: IntGenericId
    public val team: StatelessTeam
    public val channel: StatelessSchedulingChannel

    /**
     * Updates this availability, requiring a [CreateScheduleAvailabilityBuilder.start] date and an
     * [CreateScheduleAvailabilityBuilder.ending] date too.
     *
     * @param builder availability builder
     *
     * @return list of new updated availabilities
     */
    public suspend fun update(builder: CreateScheduleAvailabilityBuilder.() -> Unit): List<ScheduleAvailability> =
        client.rest.channelRoute.updateAvailability(channel.id, id, builder).second.map(client.entityDecoder::decodeScheduleAvailability)

    public suspend fun delete(): Unit =
        client.rest.channelRoute.deleteAvailability(channel.id, id)

    override suspend fun getState(): ScheduleAvailability =
        client.entityDelegator.getSchedulingChannelAvailability(id, channel.id)
            ?: error("Tried to access the state of an invalid schedule availability")
}