package com.deck.core.entity.impl

import com.deck.common.util.GenericId
import kotlinx.datetime.Instant
import com.deck.core.DeckClient
import com.deck.core.entity.User
import com.deck.core.entity.misc.DeckUserAboutInfo

public data class DeckUser(
    override val client: DeckClient,
    override val id: GenericId,
    override val name: String,
    override val subdomain: String?,
    override val avatar: String?,
    override val banner: String?,
    override val aboutInfo: DeckUserAboutInfo?,
    override val creationTime: Instant,
    override val lastLoginTime: Instant
) : User
