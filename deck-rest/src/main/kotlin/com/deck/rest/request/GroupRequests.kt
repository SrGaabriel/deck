package com.deck.rest.request

import com.deck.common.entity.RawGroup
import kotlinx.serialization.Serializable

@Serializable
public data class GetGroupResponse(
    val group: RawGroup
)

@Serializable
public data class GetGroupsResponse(
    val groups: List<RawGroup>
)