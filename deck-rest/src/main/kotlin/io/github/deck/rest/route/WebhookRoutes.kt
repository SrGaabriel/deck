package io.github.deck.rest.route

import io.github.deck.common.entity.RawWebhook
import io.github.deck.common.util.GenericId
import io.github.deck.rest.RestClient
import io.github.deck.rest.builder.CreateWebhookRequestBuilder
import io.github.deck.rest.builder.UpdateWebhookRequestBuilder
import io.github.deck.rest.request.CreateWebhookRequest
import io.github.deck.rest.request.CreateWebhookResponse
import io.github.deck.rest.request.GetServerWebhooksResponse
import io.github.deck.rest.request.UpdateWebhookRequest
import io.github.deck.rest.util.plusIf
import io.github.deck.rest.util.sendRequest
import io.ktor.http.*
import java.util.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
public class WebhookRoutes(private val client: RestClient) {
    public suspend fun createWebhook(
        serverId: GenericId,
        builder: CreateWebhookRequestBuilder.() -> Unit
    ): RawWebhook {
        contract {
            callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
        }
        return client.sendRequest<CreateWebhookResponse, CreateWebhookRequest>(
            endpoint = "/servers/${serverId}/webhooks",
            method = HttpMethod.Post,
            body = CreateWebhookRequestBuilder().apply(builder).toRequest()
        ).webhook
    }

    public suspend fun getWebhook(
        serverId: GenericId,
        webhookId: UUID
    ): RawWebhook = client.sendRequest<CreateWebhookResponse, Unit>(
        endpoint = "/servers/${serverId}/webhooks/$webhookId",
        method = HttpMethod.Get
    ).webhook

    public suspend fun getServerWebhooks(
        serverId: GenericId,
        channelId: UUID? = null
    ): List<RawWebhook> = client.sendRequest<GetServerWebhooksResponse, Unit>(
        endpoint = "/servers/${serverId}/webhooks".plusIf(channelId != null) { "?channelId=$channelId" },
        method = HttpMethod.Get
    ).webhooks

    public suspend fun updateWebhook(
        serverId: GenericId,
        webhookId: UUID,
        builder: UpdateWebhookRequestBuilder.() -> Unit
    ): RawWebhook {
        contract {
            callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
        }
        return client.sendRequest<CreateWebhookResponse, UpdateWebhookRequest>(
            endpoint = "/servers/${serverId}/webhooks/$webhookId",
            method = HttpMethod.Put,
            body = UpdateWebhookRequestBuilder().apply(builder).toRequest()
        ).webhook
    }

    public suspend fun deleteWebhook(
        serverId: GenericId,
        webhookId: UUID
    ): Unit = client.sendRequest(
        endpoint = "/servers/${serverId}/webhooks/${webhookId}",
        method = HttpMethod.Delete
    )
}