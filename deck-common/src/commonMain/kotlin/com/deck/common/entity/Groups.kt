package com.deck.common.entity

import com.deck.common.util.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RawGroup(
    val id: GenericId,
    val name: String,
    val description: String?,
    val priority: Int?,
    val type: RawChannelType,
    val avatar: String?,
    val banner: String?,
    val teamId: GenericId,
    @SerialName("gameId") val game: GameStatus?,
    // val additionalGameInfo: *,
    val visibilityTeamRoleId: IntGenericId?,
    val visibilityTeamRoleIds: List<IntGenericId>,
    val additionalVisibilityTeamRoleIds: List<IntGenericId>?,
    val membershipTeamRoleId: IntGenericId,
    val membershipTeamRoleIds: List<IntGenericId>,
    val additionalMembershipTeamRoleIds: List<IntGenericId>?,
    val isBase: Boolean,
    val createdAt: Timestamp,
    val createdBy: GenericId?,
    val updatedAt: Timestamp?,
    val updatedBy: GenericId?,
    val deletedAt: Timestamp?,
    val customReactionId: Int?,
    val isPublic: Boolean,
    val archivedAt: Timestamp?,
    val archivedBy: GenericId?,
    // val membershipUpdates: List<*>
)