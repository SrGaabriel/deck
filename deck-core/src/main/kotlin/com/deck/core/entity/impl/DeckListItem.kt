package com.deck.core.entity.impl

import com.deck.common.entity.RawListItem
import com.deck.common.util.GenericId
import com.deck.common.util.asNullable
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.ListItem
import kotlinx.datetime.Instant
import java.util.*

public data class DeckListItem(
    override val client: DeckClient,
    override val id: UUID,
    override val authorId: GenericId,
    override val serverId: GenericId,
    override val channelId: UUID,
    override val label: String,
    override val note: String?,
    override val createdAt: Instant,
    override val updatedAt: Instant?,
    override val editorId: GenericId?
): ListItem {
    public companion object {
        public fun from(client: DeckClient, raw: RawListItem): DeckListItem = DeckListItem(
            client = client,
            id = raw.id.mapToBuiltin(),
            serverId = raw.serverId,
            channelId = raw.channelId.mapToBuiltin(),
            label = raw.message,
            note = raw.note.asNullable(),
            authorId = raw.createdBy,
            createdAt = raw.createdAt,
            updatedAt = raw.updatedAt.asNullable(),
            editorId = raw.updatedBy.asNullable()
        )
    }
}