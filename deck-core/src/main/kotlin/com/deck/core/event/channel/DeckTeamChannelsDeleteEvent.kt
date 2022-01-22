package com.deck.core.event.channel

import com.deck.common.util.GenericId
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.Channel
import com.deck.core.entity.TeamChannel
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.gateway.event.type.GatewayTeamChannelsDeletedEvent
import kotlinx.coroutines.Dispatchers
import java.util.*
import kotlin.coroutines.CoroutineContext

public data class DeckTeamChannelsDeleteEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    val teamId: GenericId,
    val channels: Map<UUID, TeamChannel?>
) : DeckEvent {
    public companion object : EventMapper<GatewayTeamChannelsDeletedEvent, DeckTeamChannelsDeleteEvent> {
        override suspend fun map(client: DeckClient, event: GatewayTeamChannelsDeletedEvent): DeckTeamChannelsDeleteEvent =
            DeckTeamChannelsDeleteEvent(
                client = client,
                gatewayId = event.gatewayId,
                teamId = event.teamId,
                channels = event.channelIds.map { it.mapToBuiltin() }
                    .associateWith { client.entityCacheManager.retrieveChannel(it) as TeamChannel? }
            )
    }
}