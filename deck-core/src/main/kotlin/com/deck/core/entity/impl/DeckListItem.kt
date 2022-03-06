package com.deck.core.entity.impl

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.entity.ListItem
import kotlinx.datetime.Instant
import java.util.*

public data class DeckListItem(
    override val client: DeckClient,
    override val id: UUID,
    override val authorId: GenericId,
    override val serverId: GenericId,
    override val channelId: UUID,
    override val label: String,
    override val note: String?,
    override val createdAt: Instant
): ListItem