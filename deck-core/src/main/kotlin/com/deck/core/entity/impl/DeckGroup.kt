package com.deck.core.entity.impl

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.entity.Group
import com.deck.core.stateless.StatelessTeam

public data class DeckGroup(
    override val client: DeckClient,
    override val id: GenericId,
    override val team: StatelessTeam
): Group