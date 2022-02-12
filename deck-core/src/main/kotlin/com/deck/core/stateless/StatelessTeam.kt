package com.deck.core.stateless

import com.deck.common.util.DeckObsoleteApi
import com.deck.common.util.GenericId
import com.deck.core.entity.Group
import com.deck.core.entity.Member
import com.deck.core.entity.Team
import com.deck.core.entity.channel.TeamChannel
import com.deck.rest.builder.CreateTeamChannelBuilder
import java.util.*

public interface StatelessTeam: StatelessEntity<Team> {
    public val id: GenericId

    /**
     * Gets a channel from this team.
     *
     * @param channelId channel id
     *
     * @return channel if found, otherwise null
     */
    public suspend fun getChannel(channelId: UUID): TeamChannel? =
        client.entityDelegator.getTeamChannel(channelId, id)

    /**
     * Gets a member from this team.
     *
     * @param memberId member id
     *
     * @return member if found, otherwise null
     */
    public suspend fun getMember(memberId: GenericId): Member? =
        client.entityDelegator.getTeamMembers(id)?.firstOrNull { it.id == memberId }

    /**
     * Gets a group in this team.
     *
     * @param groupId - group id
     * @return group if found, otherwise null
     */
    public suspend fun getGroup(groupId: GenericId): Group =
        client.entityDecoder.decodeGroup(
            client.rest.groupRoute.getGroup(groupId = groupId, id)
        )

    /**
     * Gets all group in this team.
     *
     * @return all groups
     */
    @DeckObsoleteApi
    public suspend fun getGroups(): List<Group> =
        client.rest.groupRoute.getGroups(id).map(client.entityDecoder::decodeGroup)

    /**
     * Gets a channel in the specified group.
     *
     * @param group channel group
     * @return created channel
     */
    public suspend fun createChannel(group: StatelessGroup, builder: CreateTeamChannelBuilder.() -> Unit): TeamChannel =
        client.entityDecoder.decodeChannel(
            client.rest.groupRoute.createChannel(groupId = group.id, id, builder)
        ) as TeamChannel

    /**
     * Deletes the specified channel.
     *
     * @param channelId channel id
     */
    public suspend fun deleteChannel(channelId: UUID): Unit =
        client.rest.groupRoute.deleteChannel(id, channelId)

    override suspend fun getState(): Team {
        return client.entityDelegator.getTeam(id)
            ?: error("Tried to get the state of an invalid guild.")
    }
}