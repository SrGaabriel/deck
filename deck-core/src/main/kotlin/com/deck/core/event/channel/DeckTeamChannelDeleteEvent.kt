package com.deck.core.event.channel

import com.deck.common.util.GenericId
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.TeamChannel
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.gateway.event.type.GatewayTeamChannelDeletedEvent
import java.util.*

public data class DeckTeamChannelDeleteEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    val channelId: UUID,
    val teamId: GenericId,
    val old: TeamChannel?
) : DeckEvent {
    public companion object : EventMapper<GatewayTeamChannelDeletedEvent, DeckTeamChannelDeleteEvent> {
        override suspend fun map(client: DeckClient, event: GatewayTeamChannelDeletedEvent): DeckTeamChannelDeleteEvent =
            DeckTeamChannelDeleteEvent(
                client = client,
                gatewayId = event.gatewayId,
                channelId = event.channelId.mapToBuiltin(),
                teamId = event.teamId,
                old = client.entityCacheManager.retrieveChannel(event.channelId.mapToBuiltin()) as TeamChannel?
            )
    }
}