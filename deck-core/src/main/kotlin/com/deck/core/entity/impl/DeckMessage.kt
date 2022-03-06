package com.deck.core.entity.impl

import com.deck.common.content.Content
import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.entity.Message
import kotlinx.datetime.Instant
import java.util.*

public data class DeckMessage(
    override val client: DeckClient,
    override val id: UUID,
    override val content: Content,
    override val teamId: GenericId?,
    override val channelId: UUID,
    override val repliesToId: UUID?,
    override val createdAt: Instant,
    override val updatedAt: Instant?,
    override val authorId: GenericId,
    override val editorId: GenericId?,
    override val isPrivate: Boolean
) : Message