package com.deck.core.entity.impl

import com.deck.core.DeckClient
import com.deck.core.entity.Message
import com.deck.core.stateless.StatelessServer
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessMessageChannel
import kotlinx.datetime.Instant
import java.util.*

public class DeckMessage(
    override val client: DeckClient,
    override val id: UUID,
    override val content: String,
    override val channel: StatelessMessageChannel,
    override val author: StatelessUser,
    override val server: StatelessServer?,
    override val createdAt: Instant,
    override val updatedAt: Instant?,
    override val repliesTo: List<UUID>,
    override val isPrivate: Boolean
): Message