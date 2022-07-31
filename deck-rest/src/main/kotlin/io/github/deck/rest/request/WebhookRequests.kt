@file:UseSerializers(UUIDSerializer::class)

package io.github.deck.rest.request

import io.github.deck.common.entity.RawWebhook
import io.github.deck.common.util.OptionalProperty
import io.github.deck.common.util.UUIDSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
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