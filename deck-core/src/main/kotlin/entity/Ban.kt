package io.github.srgaabriel.deck.core.entity

import io.github.srgaabriel.deck.common.entity.RawServerBan
import io.github.srgaabriel.deck.common.entity.UserType
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.asNullable
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.stateless.StatelessUser
import io.github.srgaabriel.deck.core.util.BlankStatelessUser
import kotlinx.datetime.Instant

/**
 * Represents a user ban from a server
 */
public data class Ban(
    val client: DeckClient,
    val userData: ServerBannedUser,
    val reason: String?,
    val authorId: GenericId,
    val timestamp: Instant
) {
    public val user: StatelessUser get() = BlankStatelessUser(client, userData.id)
    public val author: StatelessUser get() = BlankStatelessUser(client, authorId)

    public companion object {
        public fun from(client: DeckClient, raw: RawServerBan): Ban = Ban(
            client = client,
            userData = ServerBannedUser(raw.user.id, raw.user.type, raw.user.name),
            reason = raw.reason.asNullable(),
            authorId = raw.createdBy,
            timestamp = raw.createdAt
        )
    }
}

public data class ServerBannedUser(
    val id: GenericId,
    val type: UserType,
    val name: String
)