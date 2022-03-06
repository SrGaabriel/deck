package com.deck.core.stateless

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.entity.Member
import com.deck.core.util.BlankStatelessTeam

public interface StatelessMember: StatelessEntity<Member> {
    public val id: GenericId
    public val teamId: GenericId

    public val team: StatelessTeam get() = BlankStatelessTeam(client, teamId)

    /**
     * Changes user nickname to [nickname] if present, otherwise resets user nickname.
     *
     * @param nickname null if nickname is to be reset, or new member nickname.
     */
    public suspend fun setNickname(nickname: String?) {
        if (nickname != null)
            client.rest.teamRoute.setNickname(teamId = teamId, memberId = id, nickname = nickname)
        else client.rest.teamRoute.resetNickname(teamId = teamId, memberId = id)
    }

    /**
     * Assigns specified [roleId] role to this member
     *
     * @param roleId role id
     */
    public suspend fun addRole(roleId: IntGenericId): Unit =
        client.rest.teamRoute.addRole(teamId, roleId, id)

    /**
     * Removes specified [roleId] role from this member
     *
     * @param roleId role id
     */
    public suspend fun removeRole(roleId: IntGenericId): Unit =
        client.rest.teamRoute.removeRole(teamId, roleId, id)

    override suspend fun getState(): Member {
        return client.entityDelegator.getMember(id = id, teamId = teamId)
            ?: error("Tried to access an invalid user state")
    }
}