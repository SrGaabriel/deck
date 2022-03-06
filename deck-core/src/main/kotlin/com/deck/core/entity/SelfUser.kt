package com.deck.core.entity

import com.deck.common.util.GenericId
import com.deck.core.stateless.StatelessTeam
import com.deck.core.util.BlankStatelessTeam

public interface SelfUser : User {
    public val teamIds: List<GenericId>
    public val teams: List<StatelessTeam> get() = teamIds.map { BlankStatelessTeam(client, it) }
}