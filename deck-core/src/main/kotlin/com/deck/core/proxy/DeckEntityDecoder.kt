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
import com.deck.core.util.BlankStatelessUser

public class DeckEntityDecoder(private val client: DeckClient): EntityDecoder {
    override fun decodeMessage(raw: RawMessage): Message = DeckMessage(
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

    override fun decodeListItem(raw: RawListItem): ListItem = DeckListItem(
        client = client,
        id = raw.id.mapToBuiltin(),
        serverId = raw.serverId,
        channelId = raw.channelId.mapToBuiltin(),
        label = raw.message,
        note = raw.note.asNullable(),
        authorId = raw.createdBy,
        createdAt = raw.createdAt
    )

    override fun decodeForumThread(raw: RawForumThread): ForumThread = DeckForumThread(
        client = client,
        id = raw.id,
        serverId = raw.serverId,
        channelId = raw.channelId.mapToBuiltin(),
        title = raw.title.asNullable()!!,
        content = raw.content.asNullable()!!,
        createdBy = BlankStatelessUser(client, raw.createdBy),
        createdAt = raw.createdAt,
        updatedAt = raw.updatedAt
    )

    override fun decodeDocumentation(raw: RawDocumentation): Documentation = DeckDocumentation(
        client = client,
        id = raw.id,
        title = raw.title,
        content = raw.content,
        serverId = raw.serverId,
        channelId = raw.channelId.mapToBuiltin(),
        createdAt = raw.createdAt,
        updatedAt = raw.updatedAt,
        authorId = raw.createdBy,
        editorId = raw.updatedBy
    )
}