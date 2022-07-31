package io.github.deck.core.entity.impl

import io.github.deck.common.entity.RawDocumentation
import io.github.deck.common.util.GenericId
import io.github.deck.common.util.IntGenericId
import io.github.deck.common.util.asNullable
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.Documentation
import kotlinx.datetime.Instant
import java.util.*

public data class DeckDocumentation(
    override val client: DeckClient,
    override val id: IntGenericId,
    override val title: String,
    override val content: String,
    override val serverId: GenericId,
    override val channelId: UUID,
    override val createdAt: Instant,
    override val updatedAt: Instant?,
    override val authorId: GenericId,
    override val editorId: GenericId?
): Documentation {
    public companion object {
        public fun from(client: DeckClient, raw: RawDocumentation): DeckDocumentation = DeckDocumentation(
            client = client,
            id = raw.id,
            title = raw.title,
            content = raw.content,
            serverId = raw.serverId,
            channelId = raw.channelId,
            createdAt = raw.createdAt,
            updatedAt = raw.updatedAt.asNullable(),
            authorId = raw.createdBy,
            editorId = raw.updatedBy.asNullable()
        )
    }
}