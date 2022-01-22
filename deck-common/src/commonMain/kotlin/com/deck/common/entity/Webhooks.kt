package com.deck.common.entity

import com.deck.common.util.GenericId
import kotlinx.datetime.Instant
import com.deck.common.util.UniqueId
import kotlinx.serialization.Serializable

@Serializable
public data class RawWebhook(
    val id: UniqueId,
    val name: String,
    val token: String,
    val channelId: UniqueId,
    val teamId: GenericId,
    val iconUrl: String?,
    val createdBy: GenericId,
    val createdAt: Instant,
    val deletedAt: Instant?
)
