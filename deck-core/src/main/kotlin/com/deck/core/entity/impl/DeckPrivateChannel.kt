package com.deck.core.entity.impl

import com.deck.common.entity.RawChannel
import com.deck.common.util.GenericId
import com.deck.common.util.Timestamp
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.Channel
import com.deck.core.entity.misc.ChannelContentType
import com.deck.core.entity.misc.ChannelType
import java.util.*

public class DeckPrivateChannel(
    override val client: DeckClient,
    public val raw: RawChannel
) : Channel {
    override val id: UUID get() = raw.id.mapToBuiltin()

    override val name: String get() = raw.name

    override val description: String? get() = raw.description

    override val type: ChannelType get() = raw.type

    override val contentType: ChannelContentType get() = raw.contentType

    override val createdAt: Timestamp get() = raw.createdAt

    override val createdBy: GenericId get() = raw.createdBy

    override val archivedAt: Timestamp? get() = raw.archivedAt

    override val archivedBy: GenericId? get() = raw.archivedBy

    override val updatedAt: Timestamp get() = raw.updatedAt

    override val deletedAt: Timestamp? get() = raw.deletedAt
}
