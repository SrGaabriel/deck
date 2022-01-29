package com.deck.core.proxy

import com.deck.common.entity.RawMessage
import com.deck.common.util.asNullable
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.Message
import com.deck.core.entity.impl.DeckMessage
import com.deck.core.stateless.StatelessServer
import com.deck.core.util.BlankStatelessMessageChannel
import com.deck.core.util.BlankStatelessServer
import com.deck.core.util.BlankStatelessUser

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
            isPrivate = raw.isPrivate
        )
    }
}