package com.guildedkt.entity

import com.guildedkt.util.*
import kotlinx.serialization.Serializable

@Serializable
data class RawChannel(
    val id: UniqueId,
    val type: RawChannelType,
    val createdAt: Timestamp,
    val createdBy: GenericId,
    val updatedAt: Timestamp,
    val name: String,
    val contentType: RawChannelContentType,
    val archivedAt: Timestamp?,
    val parentChannelId: UniqueId?,
    val autoArchiveAt: Timestamp?,
    val deletedAt: Timestamp?,
    val archivedBy: GenericId?,
    val description: String?,
    val createdByWebhookId: UniqueId?,
    val archivedByWebhookId: UniqueId?,
    val teamId: GenericId?,
    val channelCategoryId: UniqueId?,
    val addedAt: Timestamp?,
    val channelId: UniqueId?,
    val isRoleSynced: Boolean?,
    val roles: List<RawRole>?,
    val userPermissions: List<RawUser>?,
    @GuildedUnknown val tournamentRoles: List<Unit>? = null,
    val isPublic: Boolean,
    @GuildedUnknown val priority: Int = 0,
    val groupId: GenericId?,
    @GuildedUnknown val settings: Unit? = null,
    @GuildedUnknown val groupType: String = "",
    val rolesById: Dictionary<GenericId, RawRole>,
    @GuildedUnknown val tournamentsRolesById: Unit? = null,
    @LibraryUnsupported val createdByInfo: Unit? = null
)

@Serializable
data class RawPrivateChannel(
    val id: UniqueId,
    val type: RawChannelType,
    val createdAt: Timestamp,
    val createdBy: GenericId,
    val updatedAt: Timestamp,
    val name: String,
    val contentType: RawChannelContentType,
    val archivedAt: Timestamp?,
    val autoArchiveAt: Timestamp?,
    val archivedBy: GenericId?,
    val parentChannelId: UniqueId?,
    val deletedAt: Timestamp?,
    val createdByWebhookId: UniqueId?,
    val archivedByWebhookId: UniqueId?,
    val dmType: String = "Default",
    val ownerId: GenericId,
    val voiceParticipants: List<RawUser>
)

@Serializable(RawChannelType.Serializer::class)
enum class RawChannelType(val serialName: String) {
    Team("Team"), Private("DM");

    companion object Serializer: StringIdEnumSerializer<RawChannelType>(
        StringSerializationStrategy(values().associateBy { it.serialName })
    )
}

@Serializable(RawChannelContentType.Serializer::class)
enum class RawChannelContentType(val serialName: String) {
    Chat("chat"), Voice("voice"), Forum("forum"), Documentation("doc");

    companion object Serializer: StringIdEnumSerializer<RawChannelContentType>(
        StringSerializationStrategy(values().associateBy { it.serialName })
    )
}
