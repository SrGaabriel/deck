package com.deck.core.entity.impl

import com.deck.common.util.GenericId
import com.deck.common.util.Timestamp
import com.deck.core.entity.Message
import com.deck.core.entity.misc.Content
import java.util.*

data class DeckMessage(
    override val id: UUID,
    override val content: Content,
    override val repliesToId: UUID?,
    override val createdAt: Timestamp,
    override val updatedAt: Timestamp?,
    override val createdBy: GenericId,
    override val updatedBy: GenericId?,
    override val isSilent: Boolean,
    override val isPrivate: Boolean
) : Message
