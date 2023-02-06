package io.github.srgaabriel.deck.core.entity.impl

import io.github.srgaabriel.deck.common.entity.RawUser
import io.github.srgaabriel.deck.common.entity.UserType
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.asNullable
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.User
import kotlinx.datetime.Instant

public class DeckUser(
    override val client: DeckClient,
    override val id: GenericId,
    override val name: String,
    override val avatar: String?,
    override val banner: String?,
    override val isBot: Boolean,
    override val createdAt: Instant,
): User {
    public companion object {
        public fun from(client: DeckClient, raw: RawUser): DeckUser = DeckUser(
            client = client,
            id = raw.id,
            name = raw.name,
            avatar = raw.avatar.asNullable(),
            banner = raw.banner.asNullable(),
            isBot = raw.type == UserType.BOT,
            createdAt = raw.createdAt
        )
    }
}