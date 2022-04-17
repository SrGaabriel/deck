package io.github.deck.core.stateless

import io.github.deck.common.util.GenericId

public interface StatelessGroup: StatelessEntity {
    public val id: GenericId

    /**
     * Adds the specified member to this group
     *
     * @param memberId member's id
     */
    public suspend fun addMember(memberId: GenericId): Unit =
        client.rest.group.addMember(memberId, id)

    /**
     * Adds the specified member to this group
     *
     * @param memberId member's id
     */
    public suspend fun removeMember(memberId: GenericId): Unit =
        client.rest.group.removeMember(memberId, id)
}