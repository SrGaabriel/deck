package com.deck.common.entity

import com.deck.common.util.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Apparently, parameters [visibilityTeamRoleId], [additionalVisibilityTeamRoleIds],
 *  [membershipTeamRoleIds], [additionalMembershipTeamRoleIds] are absent in base groups*/
@Serializable
public data class RawGroup(
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
    val visibilityTeamRoleId: OptionalProperty<IntGenericId?> = OptionalProperty.NotPresent,
    val visibilityTeamRoleIds: OptionalProperty<List<IntGenericId>> = OptionalProperty.NotPresent,
    val additionalVisibilityTeamRoleIds: OptionalProperty<List<IntGenericId>?> = OptionalProperty.NotPresent,
    val membershipTeamRoleId: IntGenericId?,
    val membershipTeamRoleIds: OptionalProperty<List<IntGenericId>> = OptionalProperty.NotPresent,
    val additionalMembershipTeamRoleIds: OptionalProperty<List<IntGenericId>?> = OptionalProperty.NotPresent,
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
