package io.github.deck.core.entity

import io.github.deck.common.util.GenericId
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.stateless.StatelessWebhook
import io.github.deck.core.util.BlankStatelessUser
import io.github.deck.rest.builder.UpdateWebhookRequestBuilder
import kotlinx.datetime.Instant
import java.util.*

public interface Webhook: StatelessWebhook {
    /**  The webhook's name */
    public val name: String
    /** The id of the channel this webhook was created in */
    public val channelId: UUID

    /** The instant this webhook was created */
    public val createdAt: Instant
    /** The instant this webhook was deleted, null by default */
    public val deletedAt: Instant?

    /** The id of the user who created this webhook */
    public val creatorId: GenericId
    public val creator: StatelessUser get() = BlankStatelessUser(client, creatorId)

    /** This webhook's token */
    public val token: String?

    public suspend fun patch(builder: UpdateWebhookRequestBuilder.() -> Unit): Webhook = update {
        name = this@Webhook.name
        channelId = this@Webhook.channelId
    }
}