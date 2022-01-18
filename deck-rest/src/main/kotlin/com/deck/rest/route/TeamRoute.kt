package com.deck.rest.route

import com.deck.common.util.GenericId
import com.deck.rest.RestClient
import com.deck.rest.request.GetTeamChannelsResponse
import com.deck.rest.request.GetTeamResponse
import com.deck.rest.util.Route
import io.ktor.http.*

class TeamRoute(client: RestClient): Route(client) {
    suspend fun getTeam(teamId: GenericId) = sendRequest<GetTeamResponse, Unit>(
        "/teams/$teamId",
        HttpMethod.Get
    )

    suspend fun getTeamChannels(teamId: GenericId) = sendRequest<GetTeamChannelsResponse, Unit>(
        "/teams/$teamId/channels",
        HttpMethod.Get
    )
}
