package com.deck.core.entity.impl

import com.deck.common.util.GenericId
import kotlinx.datetime.Instant
import com.deck.core.DeckClient
import com.deck.core.entity.Member
import com.deck.core.entity.Team
import com.deck.core.stateless.StatelessMember
import com.deck.core.stateless.StatelessUser

public class DeckTeam(
    override val client: DeckClient,
    override val id: GenericId,
    override val name: String,
    override val description: String?,
    override val owner: StatelessUser,
    override val members: List<StatelessMember>,
    override val createdAt: Instant,
    override val discordGuildId: Long?,
    override val discordGuildName: String?
): Team {
    override suspend fun getMember(id: GenericId): Member? =
        members.firstOrNull { it.id == id }?.getState()
}
