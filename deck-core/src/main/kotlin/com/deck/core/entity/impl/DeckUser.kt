package com.deck.core.entity.impl

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.entity.RolePermissions
import com.deck.core.entity.User
import com.deck.core.entity.UserPermissionsOverride
import com.deck.core.entity.misc.DeckUserAboutInfo
import com.deck.core.stateless.StatelessMessageChannel
import com.deck.core.stateless.StatelessUser
import kotlinx.datetime.Instant

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

public data class DeckUserPermissionsOverride(
    override val user: StatelessUser,
    override val channel: StatelessMessageChannel?,
    override val createdAt: Instant,
    override val updatedAt: Instant?,
    override val denyPermissions: RolePermissions,
    override val allowPermissions: RolePermissions
) : UserPermissionsOverride