package com.deck.core.stateless.standard

import com.deck.core.DeckClient
import com.deck.core.stateless.StatelessTeam

public interface StandardStatelessTeamChannel: StandardStatelessChannel {
    public val client: DeckClient
    public val team: StatelessTeam

    public suspend fun delete(): Unit =
        client.rest.groupRoute.deleteChannel(teamId = team.id, channelId = id)
}