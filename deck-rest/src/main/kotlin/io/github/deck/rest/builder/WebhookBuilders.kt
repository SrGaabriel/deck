package io.github.deck.rest.builder

import io.github.deck.common.util.nullableOptional
import io.github.deck.rest.request.CreateWebhookRequest
import io.github.deck.rest.request.UpdateWebhookRequest
import io.github.deck.rest.util.required
import java.util.*

public class CreateWebhookRequestBuilder: RequestBuilder<CreateWebhookRequest> {
    public var name: String by required()
    public var channelId: UUID by required()

    override fun toRequest(): CreateWebhookRequest = CreateWebhookRequest(
        name = name,
        channelId = channelId
    )
}

public class UpdateWebhookRequestBuilder: RequestBuilder<UpdateWebhookRequest> {
    public var name: String by required()
    public var channelId: UUID? = null

    override fun toRequest(): UpdateWebhookRequest = UpdateWebhookRequest(
        name = name,
        channelId = channelId.nullableOptional()
    )
}