package io.github.deck.common.entity

import io.github.deck.common.util.GenericId
import io.github.deck.common.util.OptionalProperty
import io.github.deck.common.util.UniqueId
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