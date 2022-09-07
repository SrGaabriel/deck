@file:UseSerializers(UUIDSerializer::class)

package io.github.deck.common.entity

import io.github.deck.common.util.GenericId
import io.github.deck.common.util.OptionalProperty
import io.github.deck.common.util.UUIDSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.util.*

@Serializable
public data class RawMessage(
    public val id: UUID,
    public val type: RawMessageType,
    public val serverId: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    public val channelId: UUID,
    public val content: OptionalProperty<String> = OptionalProperty.NotPresent,
    public val embeds: List<RawEmbed> = emptyList(),
    public val replyMessageIds: OptionalProperty<List<UUID>> = OptionalProperty.NotPresent,
    public val isPrivate: Boolean = false,
    public val isSilent: Boolean = false,
    public val mentions: OptionalProperty<RawMessageMentions> = OptionalProperty.NotPresent,
    public val createdAt: Instant,
    public val createdBy: GenericId,
    public val createdByWebhookId: OptionalProperty<GenericId> = OptionalProperty.NotPresent,
    public val updatedAt: OptionalProperty<Instant> = OptionalProperty.NotPresent
)

@Serializable
public enum class RawMessageType {
    @SerialName("default")
    Default,
    @SerialName("system")
    System
}

@Serializable
public data class RawMessageMentions(
    val users: OptionalProperty<List<RawUserId>> = OptionalProperty.NotPresent,
    val channels: OptionalProperty<List<RawChannelId>> = OptionalProperty.NotPresent,
    val roles: OptionalProperty<List<RawRoleId>> = OptionalProperty.NotPresent,
    val everyone: Boolean = false,
    val here: Boolean = false
)

@Serializable
public data class RawEmbed(
    val title: OptionalProperty<String> = OptionalProperty.NotPresent,
    val description: OptionalProperty<String> = OptionalProperty.NotPresent,
    val url: OptionalProperty<String> = OptionalProperty.NotPresent,
    val timestamp: OptionalProperty<Instant> = OptionalProperty.NotPresent,
    val color: OptionalProperty<Int> = OptionalProperty.NotPresent,
    val footer: OptionalProperty<RawEmbedFooter> = OptionalProperty.NotPresent,
    val image: OptionalProperty<RawEmbedImage> = OptionalProperty.NotPresent,
    val thumbnail: OptionalProperty<RawEmbedImage> = OptionalProperty.NotPresent,
    val author: OptionalProperty<RawEmbedAuthor> = OptionalProperty.NotPresent,
    val fields: List<RawEmbedField> = emptyList()
)

@Serializable
public data class RawEmbedAuthor(
    val name: OptionalProperty<String> = OptionalProperty.NotPresent,
    val url: OptionalProperty<String> = OptionalProperty.NotPresent,
    @SerialName("icon_url")
    val iconUrl: OptionalProperty<String> = OptionalProperty.NotPresent,
)

@Serializable
public data class RawEmbedImage(
    val url: OptionalProperty<String> = OptionalProperty.NotPresent,
    val height: OptionalProperty<Int> = OptionalProperty.NotPresent,
    val width: OptionalProperty<Int> = OptionalProperty.NotPresent
)

@Serializable
public data class RawEmbedField(
    val name: String,
    val value: String,
    val inline: Boolean = false
)

@Serializable
public data class RawEmbedFooter(
    val text: String,
    @SerialName("icon_url")
    val iconUrl: OptionalProperty<String> = OptionalProperty.NotPresent
)
