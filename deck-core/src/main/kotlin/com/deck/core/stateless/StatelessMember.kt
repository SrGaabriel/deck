package com.deck.core.stateless

import com.deck.common.util.DeckDelicateApi
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

    /**
     * Assigns the specified role to this member
     *
     * @param roleId role to be assigned to this member
     */
    public suspend fun addRole(roleId: IntGenericId): Unit =
        client.rest.member.addRole(id, serverId, roleId)

    /**
     * Retrieves a list of all the ids of the roles this member is assigned to
     *
     * @return list of all the ids of the roles this member is assigned to
     */
    public suspend fun getRoleIds(): List<IntGenericId> =
        client.rest.member.getMemberRoles(id, serverId)

    /**
     * Removes the specified role from this member
     *
     * @param roleId role to be removed from this member
     */
    public suspend fun removeRole(roleId: IntGenericId): Unit =
        client.rest.member.removeRole(id, serverId, roleId)

    /**
     * Kicks this member from the server
     */
    public suspend fun kick(): Unit =
        client.rest.member.kickMember(id, serverId)

    /**
     * Bans this member from the server
     */
    public suspend fun ban(): Unit =
        client.rest.member.banMember(id, serverId)

    @DeckDelicateApi
    /** @throws [GuildedRequestException] if not found */
    public suspend fun getBan(): ServerBan =
        ServerBan.from(client, client.rest.member.getBan(id, serverId))

    /**
     * Unbans this member from the server.
     */
    public suspend fun unban(): Unit =
        client.rest.member.unbanMember(id, serverId)

    /**
     * Adds (not sets) xp to this member.
     *
     * @param amount the amount of xp to be added
     * @return user's new xp
     */
    public suspend fun awardXp(amount: Int): Int =
        client.rest.member.awardXpToMember(id, serverId, amount)
}