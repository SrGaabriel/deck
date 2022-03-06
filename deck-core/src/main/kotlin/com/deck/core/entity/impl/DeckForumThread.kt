package com.deck.core.entity.impl

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.entity.channel.ForumThread
import com.deck.core.stateless.StatelessUser
import kotlinx.datetime.Instant
import java.util.*

public data class DeckForumThread(
    override val client: DeckClient,
    override val id: Int,
    override val serverId: GenericId,
    override val channelId: UUID,
    override val title: String,
    override val content: String,
    override val createdAt: Instant,
    override val createdBy: StatelessUser,
    override val updatedAt: Instant
): ForumThread