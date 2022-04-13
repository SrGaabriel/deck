package com.deck.core.entity

import com.deck.common.entity.RawServerBan
import com.deck.common.entity.RawUserType
import com.deck.common.util.GenericId
import com.deck.common.util.asNullable
import com.deck.core.DeckClient
import com.deck.core.stateless.StatelessUser
import com.deck.core.util.BlankStatelessUser
import com.deck.core.util.EntityStrategy
import kotlinx.datetime.Instant

public data class ServerBan(
    override val client: DeckClient,
    val userData: ServerBannedUser,
    val reason: String?,
    val authorId: GenericId,
    val timestamp: Instant
): Entity {
    public val user: StatelessUser get() = BlankStatelessUser(client, userData.id)
    public val author: StatelessUser get() = BlankStatelessUser(client, authorId)

    public companion object: EntityStrategy<RawServerBan, ServerBan> {
        override fun strategize(client: DeckClient, raw: RawServerBan): ServerBan = ServerBan(
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
    val type: RawUserType,
    val name: String
)