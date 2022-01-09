package com.deck.common.entity

import com.deck.common.util.GenericId
import com.deck.common.util.Timestamp
import com.deck.common.util.UniqueId
import kotlinx.serialization.Serializable

@Serializable
data class RawWebhook(
    val id: UniqueId,
    val name: String,
    val token: String,
    val channelId: UniqueId,
    val teamId: GenericId,
    val iconUrl: String?,
    val createdBy: GenericId,
    val createdAt: Timestamp,
    val deletedAt: Timestamp?
)