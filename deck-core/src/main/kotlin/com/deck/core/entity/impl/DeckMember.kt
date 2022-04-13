package com.deck.core.entity.impl

import com.deck.common.entity.RawServerMember
import com.deck.common.entity.RawUserType
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.asNullable
import com.deck.core.DeckClient
import com.deck.core.entity.Member
import com.deck.core.util.EntityStrategy
import kotlinx.datetime.Instant

public data class DeckMember(
    override val client: DeckClient,
    override val id: GenericId,
    override val name: String,
    override val type: RawUserType,
    override val nickname: String?,
    override val roleIds: List<IntGenericId>,
    override val createdAt: Instant,
    override val joinedAt: Instant,
) : Member {
    public companion object: EntityStrategy<RawServerMember, DeckMember> {
        override fun strategize(client: DeckClient, raw: RawServerMember): DeckMember = DeckMember(
            client = client,
            id = raw.user.id,
            name = raw.user.name,
            type = raw.user.type,
            nickname = raw.nickname.asNullable(),
            roleIds = raw.roleIds,
            createdAt = raw.user.createdAt,
            joinedAt = raw.joinedAt
        )
    }
}