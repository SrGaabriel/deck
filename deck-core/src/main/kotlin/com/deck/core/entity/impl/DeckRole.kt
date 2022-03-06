package com.deck.core.entity.impl

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.DeckClient
import com.deck.core.entity.Role
import com.deck.core.entity.RolePermissions
import com.deck.core.entity.RolePermissionsOverride
import kotlinx.datetime.Instant

public data class DeckRole(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val color: String,
    override val isBase: Boolean,
    override val isDisplayedSeparately: Boolean,
    override val isMentionable: Boolean,
    override val isSelfAssignable: Boolean,
    override val name: String,
    override val createdAt: Instant,
    override val priority: Int,
    override val teamId: GenericId,
    override val updatedAt: Instant?,
    override val permissions: RolePermissions
) : Role

public data class DeckRolePermissionsOverride(
    override val teamId: GenericId,
    override val createdAt: Instant,
    override val updatedAt: Instant?,
    override val teamRoleId: IntGenericId,
    override val channelCategoryId: IntGenericId?,
    override val denyPermissions: RolePermissions,
    override val allowPermissions: RolePermissions
) : RolePermissionsOverride