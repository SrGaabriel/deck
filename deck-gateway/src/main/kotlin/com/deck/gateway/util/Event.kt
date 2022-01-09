package com.deck.gateway.util

import com.deck.common.util.GenericId
import kotlinx.serialization.Serializable

interface Event

@Serializable
data class TeamXpAddEvent(
    val type: String,
    val userIds: List<GenericId>,
    val amount: Int,
    val teamId: GenericId
): Event