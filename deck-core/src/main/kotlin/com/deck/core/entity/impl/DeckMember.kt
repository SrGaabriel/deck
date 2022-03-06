package com.deck.core.entity.impl

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.entity.Member

public data class DeckMember(
    override val client: DeckClient,
    override val id: GenericId,
    override val name: String,
    override val nickname: String?,
    override val avatar: String?,
    override val userId: GenericId,
    override val teamId: GenericId,
): Member