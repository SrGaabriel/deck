@file:UseSerializers(UUIDSerializer::class)

package io.github.srgaabriel.deck.common.entity

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.OptionalProperty
import io.github.srgaabriel.deck.common.util.UUIDSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.*

@Serializable
public data class RawWebhook(
    val id: UUID,
    val name: String,
    val serverId: GenericId,
    val channelId: UUID,
    val createdAt: Instant,
    val createdBy: GenericId,
    val deletedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent,
    val token: OptionalProperty<String> = OptionalProperty.NotPresent,
    val avatar: OptionalProperty<String> = OptionalProperty.NotPresent
)