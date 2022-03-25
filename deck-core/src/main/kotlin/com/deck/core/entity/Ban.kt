package com.deck.core.entity

import com.deck.common.entity.RawUserType
import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.stateless.StatelessUser
import com.deck.core.util.BlankStatelessUser
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
}

public data class ServerBannedUser(
    val id: GenericId,
    val type: RawUserType,
    val name: String
)