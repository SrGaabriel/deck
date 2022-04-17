package io.github.deck.core.stateless

import io.github.deck.common.util.DeckLackingDocumentation
import io.github.deck.common.util.GenericId
import io.github.deck.core.entity.Member
import io.github.deck.core.entity.MemberSummary
import io.github.deck.core.entity.ServerBan
import io.github.deck.core.entity.Webhook
import io.github.deck.core.entity.impl.DeckMember
import io.github.deck.core.entity.impl.DeckMemberSummary
import io.github.deck.core.entity.impl.DeckWebhook
import io.github.deck.rest.builder.CreateWebhookRequestBuilder
import io.github.deck.rest.builder.UpdateWebhookRequestBuilder
import io.github.deck.rest.util.GuildedRequestException
import java.util.*

public interface StatelessServer: StatelessEntity {
    public val id: GenericId

    /**
     * Retrieves a member from this server, throws exception
     * if not found (to be fixed).
     *
     * @param memberId user's id
     * @return found member
     */
    public suspend fun getMember(memberId: GenericId): Member =
        DeckMember.from(client, id, client.rest.server.getServerMember(memberId, id))

    /**
     * Retrieves all members from this server.
     *
     * @return all found members
     */
    public suspend fun getMembers(): List<MemberSummary> =
        client.rest.server.getServerMembers(id).map { rawServerMemberSummary -> DeckMemberSummary.from(client, this.id, rawServerMemberSummary) }

    /**
     * Kicks the member from this server, meaning that they'll leave
     * but can come back again at any time.
     *
     * @param memberId member's id
     */
    public suspend fun kickMember(memberId: GenericId): Unit =
        client.rest.server.kickMember(memberId, id)

    /**
     * Bans the member from this server, meaning that they'll leave
     * and won't come back, unless you unban ([unbanMember]) them.
     *
     * @param memberId member's id
     */
    public suspend fun banMember(memberId: GenericId): Unit =
        client.rest.server.banMember(memberId, id)

    /**
     * Retrieves the user's ban data, throws an exception
     * if user's not banned (this will be fixed in the future).
     *
     * @param memberId banned member's id
     * @throws [GuildedRequestException] if not found, to be fixed in the future
     * @return user's punishment data
     */
    public suspend fun getBan(memberId: GenericId): ServerBan =
        ServerBan.from(client, client.rest.server.getMemberBan(memberId, id))

    /**
     * Retrieves all bans in the server with their data.
     *
     * @return all active bans in the server
     */
    public suspend fun getBans(): List<ServerBan> =
        client.rest.server.getServerBans(id).map { ServerBan.from(client, it) }

    /**
     * Removes the ban from the specified member.
     *
     * @param memberId banned member's id
     */
    @DeckLackingDocumentation
    public suspend fun unbanMember(memberId: GenericId): Unit =
        client.rest.server.unbanMember(memberId, id)

    /**
     * Creates a webhook in this server. The channel id is specified in the [builder].
     *
     * @param builder webhook builder, requires a [CreateWebhookRequestBuilder.name] and [CreateWebhookRequestBuilder.channelId].
     * @return the created webhook
     */
    public suspend fun createWebhook(builder: CreateWebhookRequestBuilder.() -> Unit): Webhook =
        DeckWebhook.from(client, client.rest.webhook.createWebhook(id, builder))

    /**
     * Retrieves the webhook with the specified [webhookId] id.
     *
     * @param webhookId webhook's id
     * @return found webhook
     */
    public suspend fun getWebhook(webhookId: UUID): Webhook =
        DeckWebhook.from(client, client.rest.webhook.retrieveWebhook(webhookId, id))

    /**
     * Retrieves all registered webhooks in this server. If the [channelId] parameter
     * is provided, then it'll only look for webhooks in the specified channel.
     *
     * @param channelId the channel to search for the webhooks, null to search in the entirety of the server
     * @return list of all found webhooks
     */
    public suspend fun getWebhooks(channelId: UUID? = null): List<Webhook> =
        client.rest.webhook.retrieveServerWebhooks(id, channelId).map { DeckWebhook.from(client, it) }

    /**
     * Overwrites the specified webhook with the new data provided
     * inside the [builder]. It's not a [PATCH](https://tools.ietf.org/html/rfc5789) operation but instead a [PUT](https://tools.ietf.org/html/rfc7231#section-4.3.4) one.
     *
     * @param webhookId id of the webhook to be updated
     * @param builder update builder
     *
     * @return updated webhook containing new data
     */
    public suspend fun updateWebhook(webhookId: UUID, builder: UpdateWebhookRequestBuilder.() -> Unit): Webhook =
        DeckWebhook.from(client, client.rest.webhook.updateWebhook(webhookId, id, builder))

    /**
     * Deletes the webhook with the associated [webhookId] id.
     *
     * @param webhookId webhook to be deleted.
     */
    public suspend fun deleteWebhook(webhookId: UUID): Unit =
        client.rest.webhook.deleteWebhook(webhookId, id)
}