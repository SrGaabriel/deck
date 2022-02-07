package com.deck.core.entity.impl

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.entity.Group
import com.deck.core.entity.Team
import com.deck.core.stateless.StatelessMember
import com.deck.core.stateless.StatelessUser
import kotlinx.datetime.Instant

public class DeckTeam(
    override val client: DeckClient,
    override val id: GenericId,
    override val name: String,
    override val description: String?,
    override val baseGroup: Group,
    override val owner: StatelessUser,
    override val members: List<StatelessMember>,
    override val createdAt: Instant,
    override val discordGuildId: Long?,
    override val discordGuildName: String?
): Team