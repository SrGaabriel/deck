package com.guildedkt.entity

import com.guildedkt.util.GenericId
import com.guildedkt.util.Timestamp
import com.guildedkt.util.UniqueId
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