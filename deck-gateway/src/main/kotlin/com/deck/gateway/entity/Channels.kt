package com.deck.gateway.com.deck.gateway.entity

import com.deck.common.entity.RawRolePermissionsOverwritten
import com.deck.common.entity.RawUserPermission
import com.deck.common.util.Dictionary
import com.deck.common.util.IntGenericId
import com.deck.common.util.UniqueId
import kotlinx.serialization.Serializable

// Received when deleting categories
@Serializable
data class RawTeamCategoryChannel(
    val id: UniqueId,
    val channelCategoryId: IntGenericId?,
    val isRoleSynced: Boolean,
    val rolesById: Dictionary<String, RawRolePermissionsOverwritten>,
    val userPermissions: List<RawUserPermission>
)

// Received when moving categories between groups
@Serializable
data class RawTeamCategoryChannelId(val id: UniqueId)