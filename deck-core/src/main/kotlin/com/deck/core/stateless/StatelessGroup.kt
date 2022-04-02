package com.deck.core.stateless

import com.deck.common.util.GenericId

public interface StatelessGroup: StatelessEntity {
    public val id: GenericId

    public suspend fun addMember(member: StatelessMember): Unit =
        client.rest.group.addMember(member.id, id)

    public suspend fun removeMember(member: StatelessMember): Unit =
        client.rest.group.removeMember(member.id, id)
}