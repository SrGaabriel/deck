package com.deck.core.entity.impl

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.entity.Group
import com.deck.core.entity.Team
import kotlinx.datetime.Instant

public class DeckTeam(
    override val client: DeckClient,
    override val id: GenericId,
    override val name: String,
    override val description: String?,
    override val baseGroup: Group,
    override val ownerId: GenericId,
    override val memberIds: List<GenericId>,
    override val createdAt: Instant
): Team