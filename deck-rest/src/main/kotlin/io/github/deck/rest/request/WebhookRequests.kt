package io.github.deck.rest.request

import io.github.deck.common.entity.RawWebhook
import io.github.deck.common.util.OptionalProperty
import io.github.deck.common.util.UniqueId
import kotlinx.serialization.Serializable

@Serializable
public data class CreateWebhookRequest(
    public val name: String,
    public val channelId: UniqueId
)

@Serializable
public data class CreateWebhookResponse(
    public val webhook: RawWebhook
)

@Serializable
public data class UpdateWebhookRequest(
    public val name: String,
    public val channelId: OptionalProperty<UniqueId>
)

@Serializable
public data class GetServerWebhooksResponse(
    public val webhooks: List<RawWebhook>
)