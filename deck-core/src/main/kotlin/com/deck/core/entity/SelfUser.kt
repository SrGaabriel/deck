package com.deck.core.entity

import com.deck.core.stateless.StatelessTeam

public interface SelfUser : User {
    public val teams: List<StatelessTeam>
}