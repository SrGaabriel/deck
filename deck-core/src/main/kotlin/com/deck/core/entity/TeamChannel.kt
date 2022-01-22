package com.deck.core.entity

import com.deck.common.util.*
import com.deck.common.util.Dictionary
import com.deck.core.stateless.StatelessMessageChannel
import kotlinx.datetime.Instant
import java.util.*

public interface TeamChannel : Channel, StatelessMessageChannel

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

    public val userPermissions: List<UserPermission>?

    public val roles: List<Role>?
    public val rolePermissionsOverwrittenById: Dictionary<String, RolePermissionsOverwritten>
}