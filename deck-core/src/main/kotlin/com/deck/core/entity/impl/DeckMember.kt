package com.deck.core.entity.impl

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.entity.Member
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.StatelessUser

public data class DeckMember(
    override val client: DeckClient,
    override val id: GenericId,
    override val name: String,
    override val nickname: String?,
    override val avatar: String?,
    override val user: StatelessUser,
    override val team: StatelessTeam
): Member