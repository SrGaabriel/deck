package com.deck.core.entity

import com.deck.common.util.GenericId
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.StatelessWebhook
import com.deck.core.util.BlankStatelessUser
import kotlinx.datetime.Instant
import java.util.*

public interface Webhook: StatelessWebhook {
    public val name: String
    public val channelId: UUID

    public val createdAt: Instant
    public val deletedAt: Instant?

    public val creatorId: GenericId
    public val creator: StatelessUser get() = BlankStatelessUser(client, creatorId)

    public val token: String?
}