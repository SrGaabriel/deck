package com.deck.core.stateless

import com.deck.common.util.GenericId
import com.deck.core.entity.Member

public interface StatelessMember: StatelessEntity<Member> {
    public val id: GenericId

    override suspend fun getState(): Member {
        TODO("Not yet implemented")
    }
}
