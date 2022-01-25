package com.deck.core.entity

import com.deck.common.util.GenericId
import com.deck.core.stateless.StatelessMember
import com.deck.core.stateless.StatelessUser
import kotlinx.datetime.Instant

public interface Team: Entity {
    public val id: GenericId
    public val name: String

    public val description: String?
    public val owner: StatelessUser

    public val discordGuildId: Long?
    public val discordGuildName: String?

    public val members: List<StatelessMember>

    public val createdAt: Instant

    @Deprecated("To be improved")
    public suspend fun getMember(id: GenericId): Member?
}