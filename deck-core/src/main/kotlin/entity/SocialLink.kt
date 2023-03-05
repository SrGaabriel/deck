package io.github.srgaabriel.deck.core.entity

import io.github.srgaabriel.deck.common.entity.RawUserSocialLink
import io.github.srgaabriel.deck.common.entity.SocialLinkType
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.asNullable
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.stateless.StatelessUser
import io.github.srgaabriel.deck.core.util.BlankStatelessUser
import kotlinx.datetime.Instant

public data class SocialLink(
    val client: DeckClient,
    val type: SocialLinkType,
    val userId: GenericId,
    val handle: String?,
    val serviceId: String?,
    val createdAt: Instant
) {
    val user: StatelessUser get() = BlankStatelessUser(client, userId)

    public companion object {
        public fun from(client: DeckClient, raw: RawUserSocialLink): SocialLink = SocialLink(
            client = client,
            type = raw.type,
            userId = raw.userId,
            handle = raw.handle.asNullable(),
            serviceId = raw.serviceId.asNullable(),
            createdAt = raw.createdAt
        )
    }
}