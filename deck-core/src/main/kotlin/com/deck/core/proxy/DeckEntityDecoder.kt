package com.deck.core.proxy

import com.deck.common.entity.RawDocumentation
import com.deck.common.entity.RawForumThread
import com.deck.common.entity.RawListItem
import com.deck.common.entity.RawMessage
import com.deck.common.util.asNullable
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.Documentation
import com.deck.core.entity.ListItem
import com.deck.core.entity.Message
import com.deck.core.entity.channel.ForumThread
import com.deck.core.entity.impl.DeckDocumentation
import com.deck.core.entity.impl.DeckForumThread
import com.deck.core.entity.impl.DeckListItem
import com.deck.core.entity.impl.DeckMessage
import com.deck.core.stateless.StatelessServer
import com.deck.core.util.*

public class DeckEntityDecoder(private val client: DeckClient): EntityDecoder {
    override fun decodeMessage(raw: RawMessage): Message {
        val server: StatelessServer? = raw.serverId.asNullable()?.let { BlankStatelessServer(client, it) }
        return DeckMessage(
            client = client,
            id = raw.id.mapToBuiltin(),
            content = raw.content,
            author = BlankStatelessUser(client, raw.createdBy),
            channel = BlankStatelessMessageChannel(client, raw.channelId.mapToBuiltin(), server),
            server = server,
            createdAt = raw.createdAt,
            updatedAt = raw.updatedAt.asNullable(),
            repliesTo = raw.replyMessageIds.asNullable()?.map { it.mapToBuiltin() }.orEmpty(),
            isPrivate = raw.isPrivate.asNullable() == true
        )
    }

    override fun decodeListItem(raw: RawListItem): ListItem {
        val server: StatelessServer = BlankStatelessServer(client, raw.serverId)
        return DeckListItem(
            client = client,
            id = raw.id.mapToBuiltin(),
            server = server,
            channel = BlankStatelessListChannel(client, raw.channelId.mapToBuiltin(), server),
            label = raw.message,
            note = raw.note.asNullable(),
            createdBy = BlankStatelessUser(client, raw.createdBy),
            createdAt = raw.createdAt
        )
    }

    override fun decodeForumThread(raw: RawForumThread): ForumThread {
        val server: StatelessServer = BlankStatelessServer(client, raw.serverId)
        return DeckForumThread(
            client = client,
            id = raw.id,
            server = server,
            channel = BlankStatelessForumChannel(client, raw.channelId.mapToBuiltin(), server),
            title = raw.title.asNullable()!!,
            content = raw.content.asNullable()!!,
            createdBy = BlankStatelessUser(client, raw.createdBy),
            createdAt = raw.createdAt,
            updatedAt = raw.updatedAt
        )
    }

    override fun decodeDocumentation(raw: RawDocumentation): Documentation {
        val server: StatelessServer = BlankStatelessServer(client, raw.serverId)
        return DeckDocumentation(
            client = client,
            id = raw.id,
            title = raw.title,
            content = raw.content,
            server = server,
            channel = BlankStatelessDocumentationChannel(client, raw.channelId.mapToBuiltin(), server),
            createdAt = raw.createdAt,
            updatedAt = raw.updatedAt,
            createdBy = BlankStatelessUser(client, raw.createdBy),
            updatedBy = BlankStatelessUser(client, raw.updatedBy),
        )
    }
}