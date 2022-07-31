package io.github.deck.core.entity.impl

import io.github.deck.common.entity.RawServer
import io.github.deck.common.entity.ServerType
import io.github.deck.common.util.GenericId
import io.github.deck.common.util.asNullable
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.Server
import kotlinx.datetime.Instant
import java.util.*

public data class DeckServer(
    override val client: DeckClient,
    override val id: GenericId,
    override val ownerId: GenericId,
    override val type: ServerType?,
    override val name: String,
    override val about: String?,
    override val avatar: String?,
    override val banner: String?,
    override val timezone: String?,
    override val defaultChannelId: UUID?,
    override val createdAt: Instant,
): Server {
    public companion object {
        public fun from(client: DeckClient, raw: RawServer): DeckServer = DeckServer(
            client = client,
            id = raw.id,
            ownerId = raw.ownerId,
            type = raw.type.asNullable(),
            name = raw.name,
            about = raw.about.asNullable(),
            avatar = raw.avatar.asNullable(),
            banner = raw.banner.asNullable(),
            timezone = raw.timezone.asNullable(),
            defaultChannelId = raw.defaultChannelId.asNullable(),
            createdAt = raw.createdAt
        )
    }
}