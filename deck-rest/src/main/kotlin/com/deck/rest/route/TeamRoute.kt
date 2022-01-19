package com.deck.rest.route

import com.deck.common.util.GenericId
import com.deck.rest.RestClient
import com.deck.rest.request.GetTeamChannelsResponse
import com.deck.rest.request.GetTeamResponse
import com.deck.rest.util.Route
import io.ktor.http.*

public class TeamRoute(client: RestClient) : Route(client) {
    public suspend fun getTeam(teamId: GenericId): GetTeamResponse = sendRequest<GetTeamResponse, Unit>(
        "/teams/$teamId",
        HttpMethod.Get
    )

    public suspend fun getTeamChannels(teamId: GenericId): GetTeamChannelsResponse =
        sendRequest<GetTeamChannelsResponse, Unit>(
            "/teams/$teamId/channels",
            HttpMethod.Get
        )
}
