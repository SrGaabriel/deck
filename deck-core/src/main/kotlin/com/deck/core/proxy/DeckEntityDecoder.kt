package com.deck.core.proxy

import com.deck.common.entity.*
import com.deck.common.util.asNullable
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.*
import com.deck.core.entity.channel.ForumThread
import com.deck.core.entity.impl.*
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

    override fun decodeMember(raw: RawServerMember): Member = DeckMember(
        client = client,
        id = raw.user.id,
        name = raw.user.name,
        type = raw.user.type,
        nickname = raw.nickname.asNullable(),
        roleIds = raw.roleIds,
        createdAt = raw.user.createdAt,
        joinedAt = raw.joinedAt
    )

    override fun decodeBan(raw: RawServerBan): ServerBan = ServerBan(
        client = client,
        userData = ServerBannedUser(raw.user.id, raw.user.type, raw.user.name),
        reason = raw.reason.asNullable(),
        authorId = raw.createdBy,
        timestamp = raw.createdAt
    )

    override fun decodeWebhook(raw: RawWebhook): Webhook = DeckWebhook(
        client = client,
        id = raw.id.mapToBuiltin(),
        serverId = raw.serverId,
        name = raw.name,
        channelId = raw.channelId.mapToBuiltin(),
        createdAt = raw.createdAt,
        deletedAt = raw.deletedAt.asNullable(),
        creatorId = raw.createdBy,
        token = raw.token.asNullable()
    )

    override fun decodeListItem(raw: RawListItem): ListItem = DeckListItem(
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

    override fun decodeForumThread(raw: RawForumThread): ForumThread = DeckForumThread(
        client = client,
        id = raw.id,
        serverId = raw.serverId,
        channelId = raw.channelId.mapToBuiltin(),
        title = raw.title.asNullable()!!,
        content = raw.content.asNullable()!!,
        createdBy = BlankStatelessUser(client, raw.createdBy),
        createdAt = raw.createdAt,
        updatedAt = raw.updatedAt.asNullable()
    )

    override fun decodeDocumentation(raw: RawDocumentation): Documentation = DeckDocumentation(
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