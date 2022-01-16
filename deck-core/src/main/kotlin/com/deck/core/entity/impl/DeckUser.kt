package com.deck.core.com.deck.core.entity.impl

import com.deck.common.util.GenericId
import com.deck.common.util.Timestamp
import com.deck.core.com.deck.core.entity.User
import com.deck.core.com.deck.core.entity.misc.DeckUserAboutInfo

data class DeckUser(
    override val id: GenericId,
    override val name: String,
    override val subdomain: String?,
    override val avatar: String?,
    override val banner: String?,
    override val aboutInfo: DeckUserAboutInfo?,
    override val creationTime: Timestamp,
    override val lastLoginTime: Timestamp
) : User
