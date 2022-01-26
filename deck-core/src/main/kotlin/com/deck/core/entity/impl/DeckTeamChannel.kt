package com.deck.core.entity.impl

import com.deck.common.util.Dictionary
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.DeckClient
import com.deck.core.entity.Role
import com.deck.core.entity.RolePermissionsOverride
import com.deck.core.entity.UserPermissionsOverride
import com.deck.core.entity.channel.PartialTeamChannel
import com.deck.core.entity.channel.TeamChannel
import com.deck.core.entity.misc.ChannelContentType
import com.deck.core.entity.misc.ChannelType
import kotlinx.datetime.Instant
import java.util.*

public data class DeckTeamChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val name: String,
    override val description: String,
    override val type: ChannelType,
    override val contentType: ChannelContentType,
    override val createdAt: Instant,
    override val createdBy: GenericId,
    override val archivedAt: Instant?,
    override val archivedBy: GenericId?,
    override val updatedAt: Instant?,
    override val deletedAt: Instant?,
    override val teamId: GenericId
) : TeamChannel

public data class DeckPartialTeamChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val name: String,
    override val description: String,
    override val type: ChannelType,
    override val contentType: ChannelContentType,
    override val createdAt: Instant,
    override val createdBy: GenericId,
    override val archivedAt: Instant?,
    override val archivedBy: GenericId?,
    override val updatedAt: Instant?,
    override val deletedAt: Instant?,
    override val teamId: GenericId,
    override val parentChannelId: UUID?,
    override val channelCategoryId: IntGenericId?,
    override val channelId: UUID?,
    override val groupId: GenericId?,
    override val createdByWebhookId: UUID?,
    override val archivedByWebhookId: UUID?,
    override val addedAt: Instant?,
    override val autoArchiveAt: Instant?,
    override val isPublic: Boolean,
    override val isRoleSynced: Boolean?,
    override val userPermissionOverrides: List<UserPermissionsOverride>?,
    override val roles: List<Role>?,
    override val rolePermissionsOverrideById: Dictionary<String, RolePermissionsOverride>
) : PartialTeamChannel