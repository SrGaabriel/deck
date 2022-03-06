package com.deck.core.entity

import com.deck.common.util.GenericId
import com.deck.core.stateless.StatelessMember
import com.deck.core.stateless.StatelessUser
import com.deck.core.util.BlankStatelessUser

public interface Member: Entity, StatelessMember {
    public val name: String
    public val nickname: String?

    public val avatar: String?
    public val userId: GenericId

    public val user: StatelessUser get() = BlankStatelessUser(client, userId)
}