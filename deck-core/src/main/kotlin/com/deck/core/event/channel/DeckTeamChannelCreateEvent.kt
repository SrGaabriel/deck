package com.deck.core.event.channel

import com.deck.core.DeckClient
import com.deck.core.entity.TeamChannel
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.gateway.event.type.GatewayTeamChannelCreatedEvent
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

public data class DeckTeamChannelCreateEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    val channel: TeamChannel
) : DeckEvent {
    public companion object : EventMapper<GatewayTeamChannelCreatedEvent, DeckTeamChannelCreateEvent> {
        override suspend fun map(client: DeckClient, event: GatewayTeamChannelCreatedEvent): DeckTeamChannelCreateEvent =
            DeckTeamChannelCreateEvent(
                client = client,
                gatewayId = event.gatewayId,
                channel = client.entityStrategizer.decodeChannel(event.channel) as TeamChannel
            )
    }
}