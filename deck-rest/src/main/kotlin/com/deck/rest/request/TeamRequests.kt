package com.deck.rest.request

import com.deck.common.entity.RawChannel
import com.deck.common.entity.RawChannelCategory
import com.deck.rest.entity.RawFetchedTeam
import kotlinx.serialization.Serializable

@Serializable
public data class GetTeamResponse(
    val team: RawFetchedTeam
)

@Serializable
public data class GetTeamChannelsResponse(
    val channels: List<RawChannel>,
    val temporalChannels: List<RawChannel>,
    val categories: List<RawChannelCategory>
)
