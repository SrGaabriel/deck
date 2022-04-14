package com.deck.core.entity.impl

import com.deck.common.entity.RawForumThread
import com.deck.common.util.GenericId
import com.deck.common.util.asNullable
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.channel.ForumThread
import kotlinx.datetime.Instant
import java.util.*

public data class DeckForumThread(
    override val client: DeckClient,
    override val id: Int,
    override val authorId: GenericId,
    override val serverId: GenericId,
    override val channelId: UUID,
    override val title: String,
    override val content: String,
    override val createdAt: Instant,
    override val updatedAt: Instant?
): ForumThread {
    public companion object {
        public fun from(client: DeckClient, raw: RawForumThread): DeckForumThread = DeckForumThread(
            client = client,
            id = raw.id,
            authorId = raw.createdBy,
            serverId = raw.serverId,
            channelId = raw.channelId.mapToBuiltin(),
            title = raw.title.asNullable()!!,
            content = raw.content.asNullable()!!,
            createdAt = raw.createdAt,
            updatedAt = raw.updatedAt.asNullable()
        )
    }
}