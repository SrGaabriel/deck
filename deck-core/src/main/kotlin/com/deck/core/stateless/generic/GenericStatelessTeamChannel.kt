package com.deck.core.stateless.generic

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.stateless.StatelessTeam
import com.deck.core.util.BlankStatelessTeam

public interface GenericStatelessTeamChannel: GenericStatelessChannel {
    override val client: DeckClient

    override val teamId: GenericId
    override val team: StatelessTeam get() = BlankStatelessTeam(client, teamId)

    public suspend fun delete(): Unit =
        client.rest.groupRoute.deleteChannel(teamId = team.id, channelId = id)
}