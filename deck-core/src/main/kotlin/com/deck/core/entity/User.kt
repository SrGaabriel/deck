package com.deck.core.entity

import com.deck.core.stateless.StatelessUser
import kotlinx.datetime.Instant
import java.util.*

public interface User : Entity, StatelessUser {
    public val name: String
    public val subdomain: String?

    /** Null when user doesn't have an specific avatar set (default doggy avatar) */
    public val avatar: String?

    /** Null when user doesn't have a banner set (empty) */
    public val banner: String?

    public val biography: String?
    public val tagline: String?

    public val creationTime: Instant
    public val lastLoginTime: Instant
}

public interface UserPermissionsOverride {
    /** Missing when override is in a category, not a channel */
    public val channelId: UUID?

    public val createdAt: Instant
    public val updatedAt: Instant?

    public val denyPermissions: RolePermissions
    public val allowPermissions: RolePermissions
}