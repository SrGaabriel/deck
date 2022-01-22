package com.deck.core.event.channel

import com.deck.common.util.asNullable
import com.deck.core.DeckClient
import com.deck.core.entity.PartialTeamChannel
import com.deck.core.entity.TeamChannel
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.gateway.event.type.GatewayTeamChannelUpdatedEvent

public data class DeckTeamChannelUpdateEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    val channel: PartialTeamChannel,
    val old: TeamChannel?
) : DeckEvent {
    public companion object : EventMapper<GatewayTeamChannelUpdatedEvent, DeckTeamChannelUpdateEvent> {
        override suspend fun map(client: DeckClient, event: GatewayTeamChannelUpdatedEvent): DeckTeamChannelUpdateEvent? {
            // Event issued twice, this ignores the second time
            if (event.channel.type.asNullable() == null && event.channel.createdAt.asNullable() == null)
                return null

            val channel = client.entityStrategizer.decodePartialTeamChannel(event.teamId, event.channel)

            return DeckTeamChannelUpdateEvent(
                client = client,
                gatewayId = event.gatewayId,
                channel = channel,
                old = client.entityCacheManager.retrieveChannel(channel.id) as TeamChannel?
            )
        }
    }
}
