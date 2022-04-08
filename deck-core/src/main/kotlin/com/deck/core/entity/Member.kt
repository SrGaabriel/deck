package com.deck.core.entity

import com.deck.common.entity.RawUserType
import com.deck.common.util.IntGenericId
import com.deck.core.stateless.StatelessServer
import kotlinx.datetime.Instant

public interface Member: StatelessServer {
    public val name: String
    public val type: RawUserType

    public val nickname: String?
    public val roleIds: List<IntGenericId>

    public val createdAt: Instant
    public val joinedAt: Instant
}