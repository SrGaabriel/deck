package io.github.srgaabriel.deck.core.stateless

import io.github.srgaabriel.deck.common.util.DeckLackingDocumentation
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.core.entity.Ban
import io.github.srgaabriel.deck.core.entity.Member
import io.github.srgaabriel.deck.core.entity.MemberSummary
import io.github.srgaabriel.deck.core.entity.Webhook
import io.github.srgaabriel.deck.core.entity.channel.ServerChannel
import io.github.srgaabriel.deck.core.entity.impl.DeckMember
import io.github.srgaabriel.deck.core.entity.impl.DeckServerChannel
import io.github.srgaabriel.deck.core.entity.impl.DeckWebhook
import io.github.srgaabriel.deck.rest.builder.CreateChannelRequestBuilder
import io.github.srgaabriel.deck.rest.builder.CreateWebhookRequestBuilder
import io.github.srgaabriel.deck.rest.builder.UpdateWebhookRequestBuilder
import io.github.srgaabriel.deck.rest.util.GuildedRequestException
import java.util.*

public interface StatelessServer: StatelessEntity {
    public val id: GenericId

    /**
     * Creates a channel within this server
     *
     * @param builder channel builder
     * @return created channel
     */
    public suspend fun createChannel(builder: CreateChannelRequestBuilder.() -> Unit): ServerChannel =
        DeckServerChannel.from(client, client.rest.channel.createChannel {
            this.serverId = this@StatelessServer.id
            builder()
        })

    /**
     * Creates a channel within this server and tries to cast it to [T]
     *
     * @param builder channel builder
     *
     * @throws IllegalStateException if created channel type doesn't match reported type [T]
     */
    @Suppress("unchecked_cast")
    public suspend fun <T : ServerChannel> createChannelCasting(builder: CreateChannelRequestBuilder.() -> Unit): T =
        (createChannel(builder) as? T) ?: error("Reported type and actual channel type didn't match when calling 'createChannelCasting'")  

    /**
     * Retrieves a member from this server, throws exception
     * if not found (to be fixed).
     *
     * @param memberId user's id
     * @return found member
     */
    public suspend fun getMember(memberId: GenericId): Member =
        DeckMember.from(client, id, client.rest.server.getServerMember(id, memberId))

    /**
     * Retrieves all members from this server.
     *
     * @return all found members
     */
    public suspend fun getMembers(): List<MemberSummary> =
        client.rest.server.getServerMembers(id).map { rawServerMemberSummary -> MemberSummary.from(client, this.id, rawServerMemberSummary) }

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
    public suspend fun getBan(memberId: GenericId): Ban =
        Ban.from(client, client.rest.server.getMemberBan(memberId, id))

    /**
     * Retrieves all bans in the server with their data.
     *
     * @return all active bans in the server
     */
    public suspend fun getBans(): List<Ban> =
        client.rest.server.getServerBans(id).map { Ban.from(client, it) }

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
        DeckWebhook.from(client, client.rest.webhook.getWebhook(id, webhookId))

    /**
     * Retrieves all registered webhooks in this server. If the [channelId] parameter
     * is provided, then it'll only look for webhooks in the specified channel.
     *
     * @param channelId the channel to search for the webhooks, null to search in the entirety of the server
     * @return list of all found webhooks
     */
    public suspend fun getWebhooks(channelId: UUID? = null): List<Webhook> =
        client.rest.webhook.getServerWebhooks(id, channelId).map { DeckWebhook.from(client, it) }

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
        DeckWebhook.from(client, client.rest.webhook.updateWebhook(id, webhookId, builder))

    /**
     * Deletes the webhook with the associated [webhookId] id.
     *
     * @param webhookId webhook to be deleted.
     */
    public suspend fun deleteWebhook(webhookId: UUID): Unit =
        client.rest.webhook.deleteWebhook(id, webhookId)
}