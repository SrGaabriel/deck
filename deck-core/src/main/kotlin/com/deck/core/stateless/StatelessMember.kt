package com.deck.core.stateless

import com.deck.common.util.DeckObsoleteApi
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.entity.ServerBan
import com.deck.core.util.BlankStatelessServer
import com.deck.rest.util.GuildedRequestException

public interface StatelessMember: StatelessEntity {
    public val id: GenericId
    public val serverId: GenericId

    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    /**
     * Sets the nickname for this member, or removes it if the provided
     * [nickname] is null.
     *
     * @param nickname new nickname, null to reset
     *
     * @return this member's new nickname
     */
    public suspend fun setNickname(nickname: String?): String? = when(nickname) {
        null -> client.rest.memberRoute.removeMemberNickname(id, serverId).let { null }
        else -> client.rest.memberRoute.updateMemberNickname(id, serverId, nickname)
    }

    public suspend fun addRole(role: StatelessRole): Unit =
        client.rest.memberRoute.addRole(id, serverId, role.id)

    public suspend fun getRoleIds(): List<IntGenericId> =
        client.rest.memberRoute.getMemberRoles(id, serverId)

    public suspend fun removeRole(role: StatelessRole): Unit =
        client.rest.memberRoute.removeRole(id, serverId, role.id)

    public suspend fun kick(): Unit =
        client.rest.memberRoute.kickMember(id, serverId)

    public suspend fun ban(): Unit =
        client.rest.memberRoute.banMember(id, serverId)

    @DeckObsoleteApi
    /** @throws [GuildedRequestException] if not found */
    public suspend fun getBan(): ServerBan =
        client.rest.memberRoute.getBan(id, serverId).let(client.entityDecoder::decodeBan)

    public suspend fun unban(): Unit =
        client.rest.memberRoute.unbanMember(id, serverId)

    public suspend fun awardXp(amount: Int): Int =
        client.rest.memberRoute.awardXpToMember(id, serverId, amount)
}