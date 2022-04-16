package com.deck.core.entity.impl

import com.deck.common.entity.RawServerMember
import com.deck.common.entity.RawServerMemberSummary
import com.deck.common.entity.UserType
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.asNullable
import com.deck.core.DeckClient
import com.deck.core.entity.Member
import com.deck.core.entity.MemberSummary
import kotlinx.datetime.Instant

public data class DeckMember(
    override val client: DeckClient,
    override val id: GenericId,
    override val name: String,
    override val type: UserType,
    override val serverId: GenericId,
    override val nickname: String?,
    override val roleIds: List<IntGenericId>,
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
            roleIds = raw.roleIds,
            createdAt = raw.user.createdAt,
            joinedAt = raw.joinedAt
        )
    }
}

public data class DeckMemberSummary(
    override val client: DeckClient,
    override val id: GenericId,
    override val serverId: GenericId,
    override val name: String,
    override val type: UserType,
    override val roleIds: List<IntGenericId>,
): MemberSummary {
    override suspend fun getMember(): Member =
        DeckMember.from(client, serverId, client.rest.server.getServerMember(id, serverId))

    public companion object {
        public fun from(client: DeckClient, serverId: GenericId, raw: RawServerMemberSummary): DeckMemberSummary = DeckMemberSummary(
            client = client,
            id = raw.user.id,
            name = raw.user.name,
            type = raw.user.type,
            serverId = serverId,
            roleIds = raw.roleIds
        )
    }
}