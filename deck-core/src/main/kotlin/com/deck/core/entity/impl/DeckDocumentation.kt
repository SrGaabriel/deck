package com.deck.core.entity.impl

import com.deck.common.entity.RawDocumentation
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.asNullable
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.Documentation
import com.deck.rest.builder.CreateDocumentationRequestBuilder
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
    override suspend fun update(builder: CreateDocumentationRequestBuilder.() -> Unit): Documentation {
        return super.update {
            title = this@DeckDocumentation.title
            content = this@DeckDocumentation.content
            builder(this)
        }
    }

    public companion object {
        public fun from(client: DeckClient, raw: RawDocumentation): DeckDocumentation = DeckDocumentation(
            client = client,
            id = raw.id,
            title = raw.title,
            content = raw.content,
            serverId = raw.serverId,
            channelId = raw.channelId.mapToBuiltin(),
            createdAt = raw.createdAt,
            updatedAt = raw.updatedAt.asNullable(),
            authorId = raw.createdBy,
            editorId = raw.updatedBy.asNullable()
        )
    }
}