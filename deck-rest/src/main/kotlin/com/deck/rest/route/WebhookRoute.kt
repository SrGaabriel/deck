package com.deck.rest.route

import com.deck.common.entity.RawWebhook
import com.deck.common.util.GenericId
import com.deck.rest.RestClient
import com.deck.rest.builder.CreateWebhookRequestBuilder
import com.deck.rest.builder.UpdateWebhookRequestBuilder
import com.deck.rest.request.CreateWebhookRequest
import com.deck.rest.request.CreateWebhookResponse
import com.deck.rest.request.GetServerWebhooksResponse
import com.deck.rest.request.UpdateWebhookRequest
import com.deck.rest.util.Route
import com.deck.rest.util.plusIf
import io.ktor.http.*
import java.util.*

public class WebhookRoute(client: RestClient): Route(client) {
    public suspend fun createWebhook(
        serverId: GenericId,
        builder: CreateWebhookRequestBuilder.() -> Unit
    ): RawWebhook = sendRequest<CreateWebhookResponse, CreateWebhookRequest>(
        endpoint = "/servers/${serverId}/webhooks",
        method = HttpMethod.Post,
        body = CreateWebhookRequestBuilder().apply(builder).toRequest()
    ).webhook

    public suspend fun retrieveWebhook(
        webhookId: UUID,
        serverId: GenericId
    ): RawWebhook = sendRequest<CreateWebhookResponse, Unit>(
        endpoint = "/servers/${serverId}/webhooks/$webhookId",
        method = HttpMethod.Get
    ).webhook

    public suspend fun retrieveServerWebhooks(
        serverId: GenericId,
        channelId: UUID? = null
    ): List<RawWebhook> = sendRequest<GetServerWebhooksResponse, Unit>(
        endpoint = "/servers/${serverId}/webhooks".plusIf("?channelId=$channelId") { channelId != null },
        method = HttpMethod.Get
    ).webhooks

    public suspend fun updateWebhook(
        webhookId: UUID,
        serverId: GenericId,
        builder: UpdateWebhookRequestBuilder.() -> Unit
    ): RawWebhook = sendRequest<CreateWebhookResponse, UpdateWebhookRequest>(
        endpoint = "/servers/${serverId}/webhooks/$webhookId",
        method = HttpMethod.Put,
        body = UpdateWebhookRequestBuilder().apply(builder).toRequest()
    ).webhook

    public suspend fun deleteWebhook(
        webhookId: UUID,
        serverId: GenericId
    ): Unit = sendRequest<Unit, Unit>(
        endpoint = "/servers/${serverId}/webhooks/${webhookId}",
        method = HttpMethod.Delete
    )
}