package com.deck.gateway.util

import com.deck.common.util.DeckUnknown
import com.deck.common.util.GenericId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface Event

@Serializable
data class GatewayHelloPayload(
    @SerialName("sid") val sessionId: String,
    @DeckUnknown val upgrades: List<Unit>,
    val pingInterval: Long,
    val pingTimeout: Long
): Event

@Serializable
data class TeamXpAddEvent(
    val type: String,
    val userIds: List<GenericId>,
    val amount: Int,
    val teamId: GenericId
): Event