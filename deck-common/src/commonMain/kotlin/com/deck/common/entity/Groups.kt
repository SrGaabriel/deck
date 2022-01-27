package com.deck.common.entity

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.OptionalProperty
import kotlinx.datetime.Instant
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
    @SerialName("gameId") val game: Int?,
    // val additionalGameInfo: *,
    val visibilityTeamRoleId: OptionalProperty<IntGenericId?> = OptionalProperty.NotPresent,
    val visibilityTeamRoleIds: OptionalProperty<List<IntGenericId>> = OptionalProperty.NotPresent,
    val additionalVisibilityTeamRoleIds: OptionalProperty<List<IntGenericId>?> = OptionalProperty.NotPresent,
    val membershipTeamRoleId: IntGenericId?,
    val membershipTeamRoleIds: OptionalProperty<List<IntGenericId>> = OptionalProperty.NotPresent,
    val additionalMembershipTeamRoleIds: OptionalProperty<List<IntGenericId>?> = OptionalProperty.NotPresent,
    val isBase: Boolean,
    val createdAt: Instant,
    val createdBy: GenericId?,
    val updatedAt: Instant?,
    val updatedBy: GenericId?,
    val deletedAt: Instant?,
    val customReactionId: Int?,
    val isPublic: Boolean,
    val archivedAt: Instant?,
    val archivedBy: GenericId?,
    // val membershipUpdates: List<*>
)