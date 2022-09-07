package io.github.deck.core.stateless

import io.github.deck.common.util.GenericId
import io.github.deck.core.entity.Webhook
import io.github.deck.core.entity.impl.DeckWebhook
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.rest.builder.ExecuteWebhookRequestBuilder
import io.github.deck.rest.builder.UpdateWebhookRequestBuilder
import io.github.deck.rest.request.ExecuteWebhookResponse
import java.util.*

public interface StatelessWebhook: StatelessEntity {
    public val id: UUID
    public val serverId: GenericId

    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    /**
     * Executes this webhook using the provided webhook [token], sending a message with the details provided in the [builder]
     *
     * @param token webhook's token
     * @param builder message builder
     *
     * @return response
     */
    public suspend fun execute(token: String, builder: ExecuteWebhookRequestBuilder.() -> Unit): ExecuteWebhookResponse =
        client.executeWebhook(id, token, builder)

    /**
     * Overwrites this webhook with the new data provided
     * inside the [builder]. It's not a [PATCH](https://tools.ietf.org/html/rfc5789) operation but instead a [PUT](https://tools.ietf.org/html/rfc7231#section-4.3.4) one.
     *
     * @param builder update builder
     * @return updated webhook containing new data
     */
    public suspend fun update(builder: UpdateWebhookRequestBuilder.() -> Unit): Webhook =
        DeckWebhook.from(client, client.rest.webhook.updateWebhook(serverId, id, builder))

    /**
     * Deletes this webhook
     */
    public suspend fun delete(): Unit =
        client.rest.webhook.deleteWebhook(serverId, id)

    public suspend fun getWebhook(): Webhook =
        DeckWebhook.from(client, client.rest.webhook.getWebhook(serverId, id))
}