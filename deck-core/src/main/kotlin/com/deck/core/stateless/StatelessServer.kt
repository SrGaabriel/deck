package com.deck.core.stateless

import com.deck.common.util.GenericId
import com.deck.core.entity.ServerBan

public interface StatelessServer: StatelessEntity {
    public val id: GenericId

    public suspend fun kickMember(memberId: GenericId): Unit =
        client.rest.member.kickMember(memberId, id)

    public suspend fun banMember(memberId: GenericId): Unit =
        client.rest.member.banMember(memberId, id)

    public suspend fun getBan(memberId: GenericId): ServerBan? =
        client.rest.member.getBan(memberId, id).let(client.entityDecoder::decodeBan)

    public suspend fun getBans(): List<ServerBan> =
        client.rest.server.getServerBans(id).map(client.entityDecoder::decodeBan)

    public suspend fun unbanMember(memberId: GenericId): Unit =
        client.rest.member.unbanMember(memberId, id)
}