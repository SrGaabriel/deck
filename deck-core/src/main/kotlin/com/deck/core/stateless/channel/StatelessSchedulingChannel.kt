package com.deck.core.stateless.channel

import com.deck.common.util.IntGenericId
import com.deck.core.entity.channel.ScheduleAvailability
import com.deck.core.entity.channel.SchedulingChannel
import com.deck.core.stateless.StatelessEntity
import com.deck.core.stateless.generic.GenericStatelessTeamChannel
import com.deck.core.util.BlankStatelessScheduleAvailability
import com.deck.rest.builder.CreateScheduleAvailabilityBuilder

public interface StatelessSchedulingChannel: StatelessEntity<SchedulingChannel>, GenericStatelessTeamChannel {
    /**
     * Creates an availability in this scheduling channel, requires a [CreateScheduleAvailabilityBuilder.start] date
     * and an [CreateScheduleAvailabilityBuilder.ending] date.
     *
     * @param builder availability builder
     *
     * @return pair of created availability and of the all existent availabilities in the channel (inclusive)
     */
    public suspend fun createAvailability(builder: CreateScheduleAvailabilityBuilder.() -> Unit): Pair<StatelessScheduleAvailability, List<ScheduleAvailability>> =
        client.rest.channelRoute.createAvailability(id, builder).let { (availabilityId, availabilities) ->
            BlankStatelessScheduleAvailability(client, availabilityId, team, this) to availabilities.map(client.entityDecoder::decodeScheduleAvailability)
        }

    /**
     * Updates an availability in this scheduling channel, requires a [CreateScheduleAvailabilityBuilder.start] date
     * and an [CreateScheduleAvailabilityBuilder.ending] date. *Yes, you must provide both of them, it's a put operation, not a patch.*
     *
     * @param builder availability builder
     *
     * @return pair of updated availability and of the all existent availabilities in the channel (inclusive)
     */
    public suspend fun updateAvailability(availabilityId: IntGenericId, builder: CreateScheduleAvailabilityBuilder.() -> Unit): Pair<StatelessScheduleAvailability, List<ScheduleAvailability>> =
        client.rest.channelRoute.updateAvailability(id, availabilityId, builder).let { (availabilityId, availabilities) ->
            BlankStatelessScheduleAvailability(client, availabilityId, team, this) to availabilities.map(client.entityDecoder::decodeScheduleAvailability)
        }

    /**
     * Retrieves all available availabilities in this channel,
     * throwing an exception (not returning null) in case of failure.
     *
     * @return all availabilities marked in this channel.
     */
    public suspend fun retrieveAvailabilities(): List<ScheduleAvailability> =
        client.rest.channelRoute.retrieveAvailabilities(id).map(client.entityDecoder::decodeScheduleAvailability)

    /**
     * Deletes the availability matching the provided [availabilityId]
     *
     * @param availabilityId availability id
     */
    public suspend fun deleteAvailability(availabilityId: IntGenericId): Unit =
        client.rest.channelRoute.deleteAvailability(id, availabilityId)

    override suspend fun getState(): SchedulingChannel {
        return client.entityDelegator.getTeamChannel(id, team.id) as? SchedulingChannel
            ?: error("Tried to access invalid forum channel.")
    }
}