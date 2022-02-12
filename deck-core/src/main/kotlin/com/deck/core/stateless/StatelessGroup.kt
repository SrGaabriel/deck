package com.deck.core.stateless

import com.deck.common.util.GenericId
import com.deck.core.entity.Group
import com.deck.core.entity.channel.TeamChannel
import com.deck.rest.builder.CreateTeamChannelBuilder
import java.util.*

public interface StatelessGroup: StatelessEntity<Group> {
    public val id: GenericId
    public val team: StatelessTeam

    /**
     * Creates a channel in this group.
     *
     * @param builder channel builder
     * @return the created channel
     */
    public suspend fun createChannel(builder: CreateTeamChannelBuilder.() -> Unit): TeamChannel =
        client.entityDecoder.decodeChannel(
            client.rest.groupRoute.createChannel(groupId = id, team.id, builder)
        ) as TeamChannel

    /**
     * Deletes a channel from this group
     *
     * @param channelId channel id
     */
    public suspend fun deleteChannel(channelId: UUID): Unit =
        client.rest.groupRoute.deleteChannel(team.id, channelId)

    override suspend fun getState(): Group {
        TODO("Not yet implemented")
    }
}