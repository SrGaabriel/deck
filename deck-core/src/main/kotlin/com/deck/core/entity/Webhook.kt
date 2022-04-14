package com.deck.core.entity

import com.deck.common.util.GenericId
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.StatelessWebhook
import com.deck.core.util.BlankStatelessUser
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
}