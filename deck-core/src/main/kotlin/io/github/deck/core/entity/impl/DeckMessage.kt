package io.github.deck.core.entity.impl

import io.github.deck.common.entity.RawMessage
import io.github.deck.common.util.GenericId
import io.github.deck.common.util.asNullable
import io.github.deck.common.util.mapToBuiltin
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.Message
import kotlinx.datetime.Instant
import java.util.*

public data class DeckMessage(
    override val client: DeckClient,
    override val id: UUID,
    override val content: String,
    override val authorId: GenericId,
    override val serverId: GenericId?,
    override val channelId: UUID,
    override val createdAt: Instant,
    override val updatedAt: Instant?,
    override val repliesTo: List<UUID>,
    override val isPrivate: Boolean
): Message {
    public companion object {
        public fun from(client: DeckClient, raw: RawMessage): DeckMessage = DeckMessage(
            client = client,
            id = raw.id.mapToBuiltin(),
            content = raw.content,
            authorId = raw.createdBy,
            channelId = raw.channelId.mapToBuiltin(),
            serverId = raw.serverId.asNullable(),
            createdAt = raw.createdAt,
            updatedAt = raw.updatedAt.asNullable(),
            repliesTo = raw.replyMessageIds.asNullable()?.map { it.mapToBuiltin() }.orEmpty(),
            isPrivate = raw.isPrivate.asNullable() == true
        )
    }
}