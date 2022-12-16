@file:UseSerializers(UUIDSerializer::class)

package io.github.srgaabriel.deck.common.entity

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.OptionalProperty
import io.github.srgaabriel.deck.common.util.UUIDSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.*

@Serializable
public data class RawServer(
    val id: GenericId,
    val ownerId: GenericId,
    val type: OptionalProperty<ServerType> = OptionalProperty.NotPresent,
    val name: String,
    val url: OptionalProperty<String> = OptionalProperty.NotPresent,
    val about: OptionalProperty<String> = OptionalProperty.NotPresent,
    val avatar: OptionalProperty<String> = OptionalProperty.NotPresent,
    val banner: OptionalProperty<String> = OptionalProperty.NotPresent,
    val timezone: OptionalProperty<String> = OptionalProperty.NotPresent,
    val isVerified: Boolean = false,
    val defaultChannelId: OptionalProperty<UUID> = OptionalProperty.NotPresent,
    val createdAt: Instant
)

@Serializable
public enum class ServerType {
    @SerialName("team")
    Team,
    @SerialName("organization")
    Organization,
    @SerialName("community")
    Community,
    @SerialName("clan")
    Clan,
    @SerialName("guild")
    Guild,
    @SerialName("friends")
    Friends,
    @SerialName("streaming")
    Streaming,
    @SerialName("other")
    Other;
}