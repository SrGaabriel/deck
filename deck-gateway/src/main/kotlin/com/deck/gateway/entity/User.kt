package com.deck.gateway.entity

import com.deck.common.util.GenericId
import com.deck.common.util.UniqueId
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
public data class RawBot(
    val id: GenericId,
    val botId: UniqueId,
    val name: String,
    val createdAt: Instant,
    val createdBy: GenericId
)

@Serializable
public data class RawTeamMemberInfo(
    val id: GenericId,
    val nickname: String
)