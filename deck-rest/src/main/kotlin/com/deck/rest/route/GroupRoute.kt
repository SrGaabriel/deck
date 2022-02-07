package com.deck.rest.route

import com.deck.common.entity.RawChannel
import com.deck.common.entity.RawGroup
import com.deck.common.util.GenericId
import com.deck.rest.RestClient
import com.deck.rest.builder.CreateTeamChannelBuilder
import com.deck.rest.request.CreateChannelResponse
import com.deck.rest.request.CreateTeamChannelRequest
import com.deck.rest.request.GetGroupResponse
import com.deck.rest.request.GetGroupsResponse
import com.deck.rest.util.Route
import io.ktor.http.*
import java.util.*

public class GroupRoute(client: RestClient): Route(client) {
    public suspend fun getGroup(groupId: GenericId, teamId: GenericId): RawGroup = sendRequest<GetGroupResponse, Unit>(
        endpoint = "/teams/$teamId/groups/$groupId",
        method = HttpMethod.Get
    ).group

    public suspend fun getGroups(teamId: GenericId): List<RawGroup> = sendRequest<GetGroupsResponse, Unit>(
        endpoint = "/teams/$teamId/groups",
        method = HttpMethod.Get
    ).groups

    public suspend fun createChannel(
        groupId: GenericId,
        teamId: GenericId,
        builder: CreateTeamChannelBuilder.() -> Unit
    ): RawChannel = sendRequest<CreateChannelResponse, CreateTeamChannelRequest>(
        endpoint = "/teams/$teamId/groups/$groupId/channels",
        method = HttpMethod.Post,
        body = CreateTeamChannelBuilder().apply(builder).toRequest()
    ).channel

    public suspend fun deleteChannel(
        teamId: GenericId,
        channelId: UUID,
    ): Unit = sendRequest<Unit, Unit>(
        endpoint = "/teams/$teamId/groups/undefined/channels/$channelId",
        method = HttpMethod.Delete
    )
}