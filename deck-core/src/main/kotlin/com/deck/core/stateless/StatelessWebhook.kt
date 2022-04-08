package com.deck.core.stateless

import com.deck.common.util.GenericId
import com.deck.core.entity.Webhook
import com.deck.core.util.BlankStatelessServer
import com.deck.rest.builder.UpdateWebhookRequestBuilder
import java.util.*

public interface StatelessWebhook: StatelessEntity {
    public val id: UUID
    public val serverId: GenericId

    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    public suspend fun update(callback: UpdateWebhookRequestBuilder.() -> Unit): Webhook =
        client.entityDecoder.decodeWebhook(
            client.rest.webhook.updateWebhook(id, serverId, callback)
        )

    public suspend fun delete(): Unit =
        client.rest.webhook.deleteWebhook(id, serverId)
}