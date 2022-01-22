package com.deck.core.entity

import com.deck.common.util.GenericId
import kotlinx.datetime.Instant
import com.deck.core.stateless.StatelessUser

public interface Member: Entity {
    public val id: GenericId
    public val name: String
    public val nickname: String?

    public val avatar: String?
    public val user: StatelessUser

    public val joinedAt: Instant

    public suspend fun getUser(): User = user.getState()
}
