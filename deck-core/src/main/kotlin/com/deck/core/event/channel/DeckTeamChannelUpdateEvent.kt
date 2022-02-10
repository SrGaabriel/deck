package com.deck.core.event.channel

import com.deck.common.util.OptionalProperty
import com.deck.core.DeckClient
import com.deck.core.entity.channel.PartialTeamChannel
import com.deck.core.entity.channel.TeamChannel
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
            if (event.channel.type is OptionalProperty.NotPresent && event.channel.createdAt is OptionalProperty.NotPresent)
                return null
            val channel = client.entityDecoder.decodePartialTeamChannel(event.teamId, event.channel)

            return DeckTeamChannelUpdateEvent(
                client = client,
                gatewayId = event.gatewayId,
                channel = channel,
                old = client.cache.retrieveChannel(channel.id) as TeamChannel?
            )
        }
    }
}