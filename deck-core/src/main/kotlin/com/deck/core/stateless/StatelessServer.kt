package com.deck.core.stateless

import com.deck.common.util.GenericId
import com.deck.core.entity.ServerBan
import com.deck.core.entity.Webhook
import com.deck.core.entity.impl.DeckWebhook
import com.deck.rest.builder.CreateWebhookRequestBuilder
import com.deck.rest.builder.UpdateWebhookRequestBuilder
import java.util.*

public interface StatelessServer: StatelessEntity {
    public val id: GenericId

    public suspend fun kickMember(memberId: GenericId): Unit =
        client.rest.member.kickMember(memberId, id)

    public suspend fun banMember(memberId: GenericId): Unit =
        client.rest.member.banMember(memberId, id)

    public suspend fun getBan(memberId: GenericId): ServerBan? =
        ServerBan.from(client, client.rest.member.getBan(memberId, id))

    public suspend fun getBans(): List<ServerBan> =
        client.rest.server.getServerBans(id).map { ServerBan.from(client, it) }

    public suspend fun unbanMember(memberId: GenericId): Unit =
        client.rest.member.unbanMember(memberId, id)

    public suspend fun createWebhook(builder: CreateWebhookRequestBuilder.() -> Unit): Webhook =
        DeckWebhook.from(client, client.rest.webhook.createWebhook(id, builder))

    public suspend fun getWebhook(webhookId: UUID): Webhook =
        DeckWebhook.from(client, client.rest.webhook.retrieveWebhook(webhookId, id))

    public suspend fun getWebhooks(channelId: UUID? = null): List<Webhook> =
        client.rest.webhook.retrieveServerWebhooks(id, channelId).map { DeckWebhook.from(client, it) }

    public suspend fun updateWebhook(webhookId: UUID, builder: UpdateWebhookRequestBuilder.() -> Unit): Webhook =
        DeckWebhook.from(client, client.rest.webhook.updateWebhook(webhookId, id, builder))

    public suspend fun deleteWebhook(webhookId: UUID): Unit =
        client.rest.webhook.deleteWebhook(webhookId, id)
}