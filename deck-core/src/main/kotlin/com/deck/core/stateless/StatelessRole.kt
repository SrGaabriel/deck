package com.deck.core.stateless

import com.deck.common.util.IntGenericId
import com.deck.core.entity.Role

public interface StatelessRole: StatelessEntity<Role> {
    public val id: IntGenericId

    override suspend fun getState(): Role {
        TODO("Not yet implemented")
    }
}