package com.deck.core.entity.impl

import com.deck.common.entity.RawUserType
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.DeckClient
import com.deck.core.entity.Member
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
) : Member