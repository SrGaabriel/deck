package com.deck.core.entity.impl

import com.deck.core.DeckClient
import com.deck.core.entity.channel.ForumThread
import com.deck.core.stateless.StatelessServer
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessForumChannel
import kotlinx.datetime.Instant

public data class DeckForumThread(
    override val client: DeckClient,
    override val id: Int,
    override val server: StatelessServer,
    override val channel: StatelessForumChannel,
    override val title: String,
    override val content: String,
    override val createdAt: Instant,
    override val createdBy: StatelessUser,
    override val updatedAt: Instant
): ForumThread