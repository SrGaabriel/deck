package com.deck.core.event.channel

import com.deck.common.util.GenericId
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.channel.TeamChannel
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.core.stateless.StatelessTeam
import com.deck.core.util.BlankStatelessTeam
import com.deck.gateway.event.type.GatewayTeamChannelsDeletedEvent
import java.util.*

public data class DeckTeamChannelsDeleteEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    val teamId: GenericId,
    val channels: Map<UUID, TeamChannel?>
) : DeckEvent {
    public val team: StatelessTeam get() = BlankStatelessTeam(client, teamId)

    public companion object : EventMapper<GatewayTeamChannelsDeletedEvent, DeckTeamChannelsDeleteEvent> {
        override suspend fun map(client: DeckClient, event: GatewayTeamChannelsDeletedEvent): DeckTeamChannelsDeleteEvent =
            DeckTeamChannelsDeleteEvent(
                client = client,
                gatewayId = event.gatewayId,
                teamId = event.teamId,
                channels = event.channelIds.map { it.mapToBuiltin() }
                    .associateWith { client.cache.retrieveChannel(it) as TeamChannel? }
            )
    }
}