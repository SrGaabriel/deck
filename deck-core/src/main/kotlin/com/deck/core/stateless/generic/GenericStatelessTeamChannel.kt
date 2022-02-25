package com.deck.core.stateless.generic

import com.deck.core.DeckClient
import com.deck.core.stateless.StatelessTeam

public interface GenericStatelessTeamChannel: GenericStatelessChannel {
    override val client: DeckClient
    override val team: StatelessTeam

    public suspend fun delete(): Unit =
        client.rest.groupRoute.deleteChannel(teamId = team.id, channelId = id)
}