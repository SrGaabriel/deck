package com.deck.core.stateless

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.core.entity.Member

public interface StatelessMember: StatelessEntity<Member> {
    public val id: GenericId
    public val team: StatelessTeam

    /**
     * Changes user nickname to [nickname] if present, otherwise resets user nickname.
     *
     * @param nickname null if nickname is to be reset, or new member nickname.
     */
    public suspend fun setNickname(nickname: String?) {
        if (nickname != null)
            client.rest.teamRoute.setNickname(teamId = team.id, memberId = id, nickname = nickname)
        else client.rest.teamRoute.resetNickname(teamId = team.id, memberId = id)
    }

    /**
     * Assigns specified [roleId] role to this member
     *
     * @param roleId role id
     */
    public suspend fun addRole(roleId: IntGenericId): Unit =
        client.rest.teamRoute.addRole(team.id, roleId, id)

    /**
     * Removes specified [roleId] role from this member
     *
     * @param roleId role id
     */
    public suspend fun removeRole(roleId: IntGenericId): Unit =
        client.rest.teamRoute.removeRole(team.id, roleId, id)

    override suspend fun getState(): Member {
        return client.entityDelegator.getMember(id = id, teamId = team.id)
            ?: error("Tried to access an invalid user state")
    }
}