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
        null -> client.rest.member.removeMemberNickname(id, serverId).let { null }
        else -> client.rest.member.updateMemberNickname(id, serverId, nickname)
    }

    public suspend fun addRole(role: StatelessRole): Unit =
        client.rest.member.addRole(id, serverId, role.id)

    public suspend fun getRoleIds(): List<IntGenericId> =
        client.rest.member.getMemberRoles(id, serverId)

    public suspend fun removeRole(role: StatelessRole): Unit =
        client.rest.member.removeRole(id, serverId, role.id)

    public suspend fun kick(): Unit =
        client.rest.member.kickMember(id, serverId)

    public suspend fun ban(): Unit =
        client.rest.member.banMember(id, serverId)

    @DeckObsoleteApi
    /** @throws [GuildedRequestException] if not found */
    public suspend fun getBan(): ServerBan =
        client.rest.member.getBan(id, serverId).let(client.entityDecoder::decodeBan)

    public suspend fun unban(): Unit =
        client.rest.member.unbanMember(id, serverId)

    public suspend fun awardXp(amount: Int): Int =
        client.rest.member.awardXpToMember(id, serverId, amount)
}