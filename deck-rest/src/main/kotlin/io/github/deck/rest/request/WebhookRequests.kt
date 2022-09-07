@file:UseSerializers(UUIDSerializer::class)

package io.github.deck.rest.request

import io.github.deck.common.entity.RawEmbed
import io.github.deck.common.entity.RawWebhook
import io.github.deck.common.util.GenericId
import io.github.deck.common.util.OptionalProperty
import io.github.deck.common.util.UUIDSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import java.util.*

@Serializable
public data class CreateWebhookRequest(
    public val name: String,
    public val channelId: UUID
)

@Serializable
public data class CreateWebhookResponse(
    public val webhook: RawWebhook
)

@Serializable
public data class UpdateWebhookRequest(
    public val name: String,
    public val channelId: OptionalProperty<UUID>
)

@Serializable
public data class GetServerWebhooksResponse(
    public val webhooks: List<RawWebhook>
)

@Serializable
public data class ExecuteWebhookRequest(
    public val content: JsonElement? = null,
    public val embeds: List<RawEmbed> = emptyList(),
    public val isPrivate: Boolean,
    public val isSilent: Boolean,
    public val replyMessageIds: List<UUID> = emptyList(),
    public val username: String? = null,
    @SerialName("avatar_url")
    public val avatarUrl: String? = null
)

@Serializable
public data class ExecuteWebhookResponse(
    public val id: UUID,
    public val channelId: UUID,
    public val content: OptionalProperty<JsonObject> = OptionalProperty.NotPresent,
    public val embeds: OptionalProperty<List<RawEmbed>> = OptionalProperty.NotPresent,
    public val type: String,
    public val createdBy: GenericId,
    public val createdAt: Instant,
    public val webhookId: UUID
)