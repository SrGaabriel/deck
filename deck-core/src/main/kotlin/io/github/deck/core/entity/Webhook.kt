package io.github.deck.core.entity

import io.github.deck.common.util.GenericId
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.stateless.StatelessWebhook
import io.github.deck.core.stateless.channel.StatelessServerChannel
import io.github.deck.core.util.BlankStatelessServerChannel
import io.github.deck.core.util.BlankStatelessUser
import io.github.deck.rest.builder.ExecuteWebhookRequestBuilder
import io.github.deck.rest.builder.UpdateWebhookRequestBuilder
import io.github.deck.rest.request.ExecuteWebhookResponse
import kotlinx.datetime.Instant
import java.util.*

/**
 * Represents a Webhook from a Guilded server channel
 */
public interface Webhook: StatelessWebhook {
    /**  The webhook's name */
    public val name: String
    /** The id of the channel this webhook was created in */
    public val channelId: UUID
    public val channel: StatelessServerChannel get() = BlankStatelessServerChannel(client, id, serverId)

    /** The instant this webhook was created */
    public val createdAt: Instant
    /** The instant this webhook was deleted, null by default */
    public val deletedAt: Instant?

    /** The id of the user who created this webhook */
    public val creatorId: GenericId
    public val creator: StatelessUser get() = BlankStatelessUser(client, creatorId)

    /** This webhook's token */
    public val token: String?

    /**
     * Patches **(NOT UPDATES)** this webhook
     *
     * @param builder patch builder
     *
     * @return the updated webhook
     */
    public suspend fun patch(builder: UpdateWebhookRequestBuilder.() -> Unit): Webhook = update {
        name = this@Webhook.name
        channelId = this@Webhook.channelId
    }

    /**
     * Executes this webhook using its known [token], sending a message with the details provided in the [builder]
     *
     * @param builder message builder
     * @return response, null if token not known
     */
    public suspend fun execute(builder: ExecuteWebhookRequestBuilder.() -> Unit): ExecuteWebhookResponse? =
        token?.let { execute(it, builder) }
}