package io.github.deck.core.entity.impl

import io.github.deck.common.entity.RawServerChannel
import io.github.deck.common.entity.RawServerChannelType
import io.github.deck.common.util.GenericId
import io.github.deck.common.util.asNullable
import io.github.deck.common.util.mapToBuiltin
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.channel.*
import kotlinx.datetime.Instant
import java.util.*

public class DeckServerChannel internal constructor(
    override val client: DeckClient,
    override val id: UUID,
    override val name: String,
    override val topic: String?,
    override val type: RawServerChannelType,
    override val createdAt: Instant,
    override val archivedAt: Instant?,
    override val creatorId: GenericId,
    override val archiverId: GenericId?,
    override val updatedAt: Instant?,
    override val serverId: GenericId,
    override val groupId: GenericId,
    override val isPublic: Boolean,
): ServerChannel {
    public companion object {
        public fun from(client: DeckClient, raw: RawServerChannel): ServerChannel = when (raw.type) {
            RawServerChannelType.CHAT -> DeckServerMessageChannel(
                client = client,
                id = raw.id.mapToBuiltin(),
                name = raw.name,
                topic = raw.topic.asNullable(),
                type = raw.type,
                createdAt = raw.createdAt,
                archivedAt = raw.archivedAt.asNullable(),
                creatorId = raw.createdBy,
                archiverId = raw.archivedBy.asNullable(),
                updatedAt = raw.updatedAt.asNullable(),
                serverId = raw.serverId,
                groupId = raw.groupId,
                isPublic = raw.isPublic,
            )
            RawServerChannelType.FORUMS -> DeckForumChannel(
                client = client,
                id = raw.id.mapToBuiltin(),
                name = raw.name,
                topic = raw.topic.asNullable(),
                type = raw.type,
                createdAt = raw.createdAt,
                archivedAt = raw.archivedAt.asNullable(),
                creatorId = raw.createdBy,
                archiverId = raw.archivedBy.asNullable(),
                updatedAt = raw.updatedAt.asNullable(),
                serverId = raw.serverId,
                groupId = raw.groupId,
                isPublic = raw.isPublic,
            )
            RawServerChannelType.LIST -> DeckListChannel(
                client = client,
                id = raw.id.mapToBuiltin(),
                name = raw.name,
                topic = raw.topic.asNullable(),
                type = raw.type,
                createdAt = raw.createdAt,
                archivedAt = raw.archivedAt.asNullable(),
                creatorId = raw.createdBy,
                archiverId = raw.archivedBy.asNullable(),
                updatedAt = raw.updatedAt.asNullable(),
                serverId = raw.serverId,
                groupId = raw.groupId,
                isPublic = raw.isPublic,
            )
            RawServerChannelType.DOCS -> DeckDocumentationChannel(
                client = client,
                id = raw.id.mapToBuiltin(),
                name = raw.name,
                topic = raw.topic.asNullable(),
                type = raw.type,
                createdAt = raw.createdAt,
                archivedAt = raw.archivedAt.asNullable(),
                creatorId = raw.createdBy,
                archiverId = raw.archivedBy.asNullable(),
                updatedAt = raw.updatedAt.asNullable(),
                serverId = raw.serverId,
                groupId = raw.groupId,
                isPublic = raw.isPublic,
            )
            RawServerChannelType.CALENDAR -> DeckCalendarChannel(
                client = client,
                id = raw.id.mapToBuiltin(),
                name = raw.name,
                topic = raw.topic.asNullable(),
                type = raw.type,
                createdAt = raw.createdAt,
                archivedAt = raw.archivedAt.asNullable(),
                creatorId = raw.createdBy,
                archiverId = raw.archivedBy.asNullable(),
                updatedAt = raw.updatedAt.asNullable(),
                serverId = raw.serverId,
                groupId = raw.groupId,
                isPublic = raw.isPublic,
            )
            else -> DeckServerChannel(
                client = client,
                id = raw.id.mapToBuiltin(),
                name = raw.name,
                topic = raw.topic.asNullable(),
                type = raw.type,
                createdAt = raw.createdAt,
                archivedAt = raw.archivedAt.asNullable(),
                creatorId = raw.createdBy,
                archiverId = raw.archivedBy.asNullable(),
                updatedAt = raw.updatedAt.asNullable(),
                serverId = raw.serverId,
                groupId = raw.groupId,
                isPublic = raw.isPublic,
            )
        }
    }
}

public data class DeckMessageChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val name: String,
    override val topic: String?,
    override val type: RawServerChannelType,
    override val createdAt: Instant,
    override val archivedAt: Instant?,
    override val creatorId: GenericId,
    override val archiverId: GenericId?,
    override val updatedAt: Instant?,
    override val serverId: GenericId?,
    override val isPublic: Boolean,
): MessageChannel

public data class DeckServerMessageChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val name: String,
    override val topic: String?,
    override val type: RawServerChannelType,
    override val createdAt: Instant,
    override val archivedAt: Instant?,
    override val creatorId: GenericId,
    override val archiverId: GenericId?,
    override val updatedAt: Instant?,
    override val serverId: GenericId,
    override val groupId: GenericId,
    override val isPublic: Boolean,
): ServerMessageChannel

public data class DeckForumChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val name: String,
    override val topic: String?,
    override val type: RawServerChannelType,
    override val createdAt: Instant,
    override val archivedAt: Instant?,
    override val creatorId: GenericId,
    override val archiverId: GenericId?,
    override val updatedAt: Instant?,
    override val serverId: GenericId,
    override val groupId: GenericId,
    override val isPublic: Boolean
): ForumChannel

public data class DeckListChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val name: String,
    override val topic: String?,
    override val type: RawServerChannelType,
    override val createdAt: Instant,
    override val archivedAt: Instant?,
    override val creatorId: GenericId,
    override val archiverId: GenericId?,
    override val updatedAt: Instant?,
    override val serverId: GenericId,
    override val groupId: GenericId,
    override val isPublic: Boolean
): ListChannel

public data class DeckDocumentationChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val name: String,
    override val topic: String?,
    override val type: RawServerChannelType,
    override val createdAt: Instant,
    override val archivedAt: Instant?,
    override val creatorId: GenericId,
    override val archiverId: GenericId?,
    override val updatedAt: Instant?,
    override val serverId: GenericId,
    override val groupId: GenericId,
    override val isPublic: Boolean
): DocumentationChannel

public data class DeckCalendarChannel(
    override val client: DeckClient,
    override val id: UUID,
    override val name: String,
    override val topic: String?,
    override val type: RawServerChannelType,
    override val createdAt: Instant,
    override val archivedAt: Instant?,
    override val creatorId: GenericId,
    override val archiverId: GenericId?,
    override val updatedAt: Instant?,
    override val serverId: GenericId,
    override val groupId: GenericId,
    override val isPublic: Boolean
): CalendarChannel