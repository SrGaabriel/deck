package io.github.srgaabriel.deck.core.entity.impl

import io.github.srgaabriel.deck.common.entity.RawServerChannel
import io.github.srgaabriel.deck.common.entity.ServerChannelType
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.asNullable
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.channel.*
import kotlinx.datetime.Instant
import java.util.*

public class DeckServerChannel internal constructor(
    override val client: DeckClient,
    override val id: UUID,
    override val name: String,
    override val topic: String?,
    override val type: ServerChannelType,
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
            ServerChannelType.Chat -> DeckServerMessageChannel(
                client = client,
                id = raw.id,
                name = raw.name,
                topic = raw.topic.asNullable(),
                createdAt = raw.createdAt,
                archivedAt = raw.archivedAt.asNullable(),
                creatorId = raw.createdBy,
                archiverId = raw.archivedBy.asNullable(),
                updatedAt = raw.updatedAt.asNullable(),
                serverId = raw.serverId,
                groupId = raw.groupId,
                isPublic = raw.isPublic,
            )
            ServerChannelType.Forums -> DeckForumChannel(
                client = client,
                id = raw.id,
                name = raw.name,
                topic = raw.topic.asNullable(),
                createdAt = raw.createdAt,
                archivedAt = raw.archivedAt.asNullable(),
                creatorId = raw.createdBy,
                archiverId = raw.archivedBy.asNullable(),
                updatedAt = raw.updatedAt.asNullable(),
                serverId = raw.serverId,
                groupId = raw.groupId,
                isPublic = raw.isPublic,
            )
            ServerChannelType.List -> DeckListChannel(
                client = client,
                id = raw.id,
                name = raw.name,
                topic = raw.topic.asNullable(),
                createdAt = raw.createdAt,
                archivedAt = raw.archivedAt.asNullable(),
                creatorId = raw.createdBy,
                archiverId = raw.archivedBy.asNullable(),
                updatedAt = raw.updatedAt.asNullable(),
                serverId = raw.serverId,
                groupId = raw.groupId,
                isPublic = raw.isPublic,
            )
            ServerChannelType.Documentation -> DeckDocumentationChannel(
                client = client,
                id = raw.id,
                name = raw.name,
                topic = raw.topic.asNullable(),
                createdAt = raw.createdAt,
                archivedAt = raw.archivedAt.asNullable(),
                creatorId = raw.createdBy,
                archiverId = raw.archivedBy.asNullable(),
                updatedAt = raw.updatedAt.asNullable(),
                serverId = raw.serverId,
                groupId = raw.groupId,
                isPublic = raw.isPublic,
            )
            ServerChannelType.Calendar -> DeckCalendarChannel(
                client = client,
                id = raw.id,
                name = raw.name,
                topic = raw.topic.asNullable(),
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
                id = raw.id,
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
    override val createdAt: Instant,
    override val archivedAt: Instant?,
    override val creatorId: GenericId,
    override val archiverId: GenericId?,
    override val updatedAt: Instant?,
    override val serverId: GenericId,
    override val groupId: GenericId,
    override val isPublic: Boolean
): CalendarChannel