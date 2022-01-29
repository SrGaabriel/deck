package com.deck.common.entity

import com.deck.common.util.GenericId
import com.deck.common.util.OptionalProperty
import com.deck.common.util.UniqueId
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class RawMessage(
    public val id: UniqueId,
    public val type: RawMessageType,
    public val serverId: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    public val channelId: UniqueId,
    public val content: String,
    public val replyMessageIds: OptionalProperty<List<UniqueId>> = OptionalProperty.NotPresent,
    public val isPrivate: OptionalProperty<Boolean> = OptionalProperty.NotPresent,
    public val createdAt: Instant,
    public val createdBy: GenericId,
    public val createdByWebhookId: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    public val updatedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent
)

@Serializable
public enum class RawMessageType {
    @SerialName("default")
    DEFAULT,
    @SerialName("system")
    SYSTEM
}