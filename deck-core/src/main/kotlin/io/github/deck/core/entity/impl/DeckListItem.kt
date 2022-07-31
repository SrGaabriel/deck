package io.github.deck.core.entity.impl

import io.github.deck.common.entity.RawListItem
import io.github.deck.common.util.GenericId
import io.github.deck.common.util.asNullable
import io.github.deck.common.util.map
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.ListItem
import io.github.deck.core.entity.ListItemNote
import kotlinx.datetime.Instant
import java.util.*

public data class DeckListItem(
    override val client: DeckClient,
    override val id: UUID,
    override val authorId: GenericId,
    override val serverId: GenericId,
    override val channelId: UUID,
    override val label: String,
    override val note: ListItemNote?,
    override val createdAt: Instant,
    override val updatedAt: Instant?,
    override val editorId: GenericId?
): ListItem {
    public companion object {
        public fun from(client: DeckClient, raw: RawListItem): DeckListItem = DeckListItem(
            client = client,
            id = raw.id,
            serverId = raw.serverId,
            channelId = raw.channelId,
            label = raw.message,
            note = raw.note.map { ListItemNote.from(client, it) }.asNullable(),
            authorId = raw.createdBy,
            createdAt = raw.createdAt,
            updatedAt = raw.updatedAt.asNullable(),
            editorId = raw.updatedBy.asNullable()
        )
    }
}