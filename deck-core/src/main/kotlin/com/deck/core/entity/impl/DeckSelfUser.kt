package com.deck.core.entity.impl

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.entity.SelfUser
import com.deck.core.entity.misc.DeckUserAboutInfo
import com.deck.core.stateless.StatelessTeam
import kotlinx.datetime.Instant

public data class DeckSelfUser constructor(
    override val client: DeckClient,
    override val id: GenericId,
    override var name: String,
    override var subdomain: String?,
    override var avatar: String?,
    override var banner: String?,
    override var aboutInfo: DeckUserAboutInfo?,
    override val creationTime: Instant,
    override val lastLoginTime: Instant,
    override val teams: List<StatelessTeam>
) : SelfUser