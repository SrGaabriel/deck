package com.deck.core.stateless

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId

public interface StatelessMember: StatelessEntity {
    public val id: GenericId
    public val server: StatelessServer

    /**
     * Sets the nickname for this member, or removes it if the provided
     * [nickname] is null.
     *
     * @param nickname new nickname, null to reset
     *
     * @return this member's new nickname
     */
    public suspend fun setNickname(nickname: String?): String? = when(nickname) {
        null -> client.rest.memberRoute.removeMemberNickname(id, server.id).let { null }
        else -> client.rest.memberRoute.updateMemberNickname(id, server.id, nickname)
    }

    public suspend fun addRole(role: StatelessRole): Unit =
        client.rest.memberRoute.addRole(id, server.id, role.id)

    public suspend fun getRoleIds(): List<IntGenericId> =
        client.rest.memberRoute.getMemberRoles(id, server.id)

    public suspend fun removeRole(role: StatelessRole): Unit =
        client.rest.memberRoute.removeRole(id, server.id, role.id)

    public suspend fun awardXp(amount: Int): Int =
        client.rest.memberRoute.awardXpToMember(id, server.id, amount)
}