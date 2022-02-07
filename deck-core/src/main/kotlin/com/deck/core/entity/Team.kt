package com.deck.core.entity

import com.deck.core.stateless.StatelessMember
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.StatelessUser
import kotlinx.datetime.Instant

public interface Team: Entity, StatelessTeam {
    public val name: String

    public val description: String?

    public val baseGroup: Group
    public val owner: StatelessUser

    public val discordGuildId: Long?
    public val discordGuildName: String?

    public val members: List<StatelessMember>

    public val createdAt: Instant
}