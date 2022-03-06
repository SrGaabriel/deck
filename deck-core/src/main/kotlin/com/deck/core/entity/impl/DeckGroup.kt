package com.deck.core.entity.impl

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.entity.Group

public data class DeckGroup(
    override val client: DeckClient,
    override val id: GenericId,
    override val teamId: GenericId
): Group