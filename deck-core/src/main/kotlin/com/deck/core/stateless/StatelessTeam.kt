package com.deck.core.stateless

import com.deck.common.util.GenericId
import com.deck.core.entity.Group
import com.deck.core.entity.Member
import com.deck.core.entity.Team
import com.deck.core.entity.channel.TeamChannel
import com.deck.rest.builder.CreateTeamChannelBuilder
import java.util.*

public interface StatelessTeam: StatelessEntity<Team> {
    public val id: GenericId

    public suspend fun getChannel(channelId: UUID): TeamChannel? =
        client.entityDelegator.getTeamChannel(channelId, id)

    public suspend fun getMember(memberId: GenericId): Member? =
        client.entityDelegator.getTeamMembers(id)?.firstOrNull { it.id == memberId }

    public suspend fun getGroup(groupId: GenericId): Group =
        client.entityDecoder.decodeGroup(
            client.rest.groupRoute.getGroup(groupId = groupId, id)
        )

    public suspend fun getGroups(): List<Group> =
        client.rest.groupRoute.getGroups(id).map(client.entityDecoder::decodeGroup)

    public suspend fun createChannel(group: StatelessGroup, builder: CreateTeamChannelBuilder.() -> Unit): TeamChannel =
        client.entityDecoder.decodeChannel(
            client.rest.groupRoute.createChannel(groupId = group.id, id, builder)
        ) as TeamChannel

    public suspend fun deleteChannel(channelId: UUID): Unit =
        client.rest.groupRoute.deleteChannel(id, channelId)

    override suspend fun getState(): Team {
        return client.entityDelegator.getTeam(id)
            ?: error("Tried to get the state of an invalid guild.")
    }
}