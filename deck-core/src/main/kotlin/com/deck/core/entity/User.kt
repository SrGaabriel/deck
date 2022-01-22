package com.deck.core.entity

import com.deck.common.util.GenericId
import kotlinx.datetime.Instant
import com.deck.core.entity.misc.DeckUserAboutInfo

public interface User : Entity {
    public val id: GenericId
    public val name: String
    public val subdomain: String?

    /** Null when user doesn't have an specific avatar set (default doggy avatar) */
    public val avatar: String?

    /** Null when user doesn't have a banner set (empty) */
    public val banner: String?

    public val aboutInfo: DeckUserAboutInfo?

    public val creationTime: Instant
    public val lastLoginTime: Instant
}
