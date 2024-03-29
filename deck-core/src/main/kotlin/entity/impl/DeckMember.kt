package io.github.srgaabriel.deck.core.entity.impl

import io.github.srgaabriel.deck.common.entity.RawServerMember
import io.github.srgaabriel.deck.common.entity.UserType
import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.common.util.IntGenericId
import io.github.srgaabriel.deck.common.util.asNullable
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.Member
import kotlinx.datetime.Instant

public data class DeckMember(
    override val client: DeckClient,
    override val id: GenericId,
    override val name: String,
    override val type: UserType,
    override val serverId: GenericId,
    override val nickname: String?,
    override val avatar: String?,
    override val banner: String?,
    override val roleIds: List<IntGenericId>,
    override val isOwner: Boolean,
    override val createdAt: Instant,
    override val joinedAt: Instant,
) : Member {
    public companion object {
        public fun from(client: DeckClient, serverId: GenericId, raw: RawServerMember): DeckMember = DeckMember(
            client = client,
            id = raw.user.id,
            name = raw.user.name,
            type = raw.user.type,
            serverId = serverId,
            nickname = raw.nickname.asNullable(),
            avatar = raw.user.avatar.asNullable(),
            banner = raw.user.banner.asNullable(),
            roleIds = raw.roleIds,
            isOwner = raw.isOwner,
            createdAt = raw.user.createdAt,
            joinedAt = raw.joinedAt
        )
    }
}