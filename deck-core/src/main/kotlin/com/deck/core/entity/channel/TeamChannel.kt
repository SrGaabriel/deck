package com.deck.core.entity.channel

import com.deck.common.util.Dictionary
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.entity.Role
import com.deck.core.entity.RolePermissionsOverride
import com.deck.core.entity.UserPermissionsOverride
import com.deck.core.stateless.StatelessTeam
import kotlinx.datetime.Instant
import java.util.*

public interface TeamChannel : Channel {
    override val team: StatelessTeam
}

public interface PartialTeamChannel : TeamChannel {
    public val parentChannelId: UUID?
    public val channelCategoryId: IntGenericId?
    public val channelId: UUID?
    public val groupId: GenericId?

    public val createdByWebhookId: UUID?
    public val archivedByWebhookId: UUID?

    public val addedAt: Instant?
    public val autoArchiveAt: Instant?

    public val isPublic: Boolean
    public val isRoleSynced: Boolean?

    public val userPermissionOverrides: List<UserPermissionsOverride>?

    public val roles: List<Role>?
    public val rolePermissionsOverrideById: Dictionary<String, RolePermissionsOverride>
}