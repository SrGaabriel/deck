package com.deck.core.stateless

import com.deck.common.util.GenericId
import com.deck.core.entity.Webhook
import com.deck.core.entity.impl.DeckWebhook
import com.deck.core.util.BlankStatelessServer
import com.deck.rest.builder.UpdateWebhookRequestBuilder
import java.util.*

public interface StatelessWebhook: StatelessEntity {
    public val id: UUID
    public val serverId: GenericId

    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    /**
     * Overwrites this webhook with the new data provided
     * inside the [builder]. It's not a [PATCH](https://tools.ietf.org/html/rfc5789) operation but instead a [PUT](https://tools.ietf.org/html/rfc7231#section-4.3.4) one.
     *
     * @param builder update builder
     * @return updated webhook containing new data
     */
    public suspend fun update(builder: UpdateWebhookRequestBuilder.() -> Unit): Webhook =
        DeckWebhook.from(client, client.rest.webhook.updateWebhook(id, serverId, builder))

    /**
     * Deletes this webhook
     */
    public suspend fun delete(): Unit =
        client.rest.webhook.deleteWebhook(id, serverId)
}