package com.deck.core.entity.impl

import com.deck.core.DeckClient
import com.deck.core.entity.ListItem
import com.deck.core.stateless.StatelessServer
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessListChannel
import kotlinx.datetime.Instant
import java.util.*

public data class DeckListItem(
    override val client: DeckClient,
    override val id: UUID,
    override val server: StatelessServer,
    override val channel: StatelessListChannel,
    override val label: String,
    override val note: String?,
    override val createdBy: StatelessUser,
    override val createdAt: Instant
): ListItem