package com.deck.core.entity.impl

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.entity.RolePermissions
import com.deck.core.entity.User
import com.deck.core.entity.UserPermissionsOverride
import kotlinx.datetime.Instant
import java.util.*

public data class DeckUser(
    override val client: DeckClient,
    override val id: GenericId,
    override val name: String,
    override val subdomain: String?,
    override val avatar: String?,
    override val banner: String?,
    override val biography: String?,
    override val tagline: String?,
    override val creationTime: Instant,
    override val lastLoginTime: Instant
) : User

public data class DeckUserPermissionsOverride(
    /** Missing when override is in a category, not a channel */
    override val channelId: UUID?,
    override val createdAt: Instant,
    override val updatedAt: Instant?,
    override val denyPermissions: RolePermissions,
    override val allowPermissions: RolePermissions
) : UserPermissionsOverride