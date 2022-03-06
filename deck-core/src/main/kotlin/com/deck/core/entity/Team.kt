package com.deck.core.entity

import com.deck.common.util.GenericId
import com.deck.core.stateless.StatelessMember
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.StatelessUser
import com.deck.core.util.BlankStatelessMember
import com.deck.core.util.BlankStatelessUser
import kotlinx.datetime.Instant

public interface Team: Entity, StatelessTeam {
    public val name: String

    public val description: String?

    public val baseGroup: Group
    public val ownerId: GenericId

    public val owner: StatelessUser get() = BlankStatelessUser(client, ownerId)

    public val memberIds: List<GenericId>
    public val members: List<StatelessMember> get() = memberIds.map { BlankStatelessMember(client, it, this.id) }

    public val createdAt: Instant
}