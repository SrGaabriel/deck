package com.deck.core.entity.impl

import com.deck.common.entity.RawMessage
import com.deck.common.util.GenericId
import com.deck.common.util.asNullable
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.Message
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