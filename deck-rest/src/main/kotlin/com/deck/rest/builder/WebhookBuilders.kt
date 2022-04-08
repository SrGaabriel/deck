package com.deck.rest.builder

import com.deck.common.util.mapToModel
import com.deck.common.util.nullableOptional
import com.deck.rest.request.CreateWebhookRequest
import com.deck.rest.request.UpdateWebhookRequest
import com.deck.rest.util.required
import java.util.*

public class CreateWebhookRequestBuilder: RequestBuilder<CreateWebhookRequest> {
    public var name: String by required()
    public var channelId: UUID by required()

    override fun toRequest(): CreateWebhookRequest = CreateWebhookRequest(
        name = name,
        channelId = channelId.mapToModel()
    )
}

public class UpdateWebhookRequestBuilder: RequestBuilder<UpdateWebhookRequest> {
    public var name: String by required()
    public var channelId: UUID? = null

    override fun toRequest(): UpdateWebhookRequest = UpdateWebhookRequest(
        name = name,
        channelId = channelId?.mapToModel().nullableOptional()
    )
}