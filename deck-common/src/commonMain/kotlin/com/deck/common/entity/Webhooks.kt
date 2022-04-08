package com.deck.common.entity

import com.deck.common.util.GenericId
import com.deck.common.util.OptionalProperty
import com.deck.common.util.UniqueId
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
public data class RawWebhook(
    val id: UniqueId,
    val name: String,
    val serverId: GenericId,
    val channelId: UniqueId,
    val createdAt: Instant,
    val createdBy: GenericId,
    val deletedAt: OptionalProperty<Instant>,
    val token: OptionalProperty<String>
)