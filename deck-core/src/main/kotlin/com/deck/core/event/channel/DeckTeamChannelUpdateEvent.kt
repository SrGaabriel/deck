package com.deck.core.event.channel

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.entity.impl.DeckPartialTeamChannel
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.gateway.event.type.GatewayTeamChannelUpdatedEvent
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

public data class DeckTeamChannelUpdateEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    val teamId: GenericId,
    val channel: DeckPartialTeamChannel
) : DeckEvent {
    public companion object : EventMapper<GatewayTeamChannelUpdatedEvent, DeckTeamChannelUpdateEvent> {
        override suspend fun map(
            client: DeckClient,
            event: GatewayTeamChannelUpdatedEvent
        ): DeckTeamChannelUpdateEvent =
            DeckTeamChannelUpdateEvent(
                client,
                event.gatewayId,
                event.teamId,
                client.entityStrategizer.decodeTeamPartialChannel(event.channel) as DeckPartialTeamChannel
            )

        override val coroutineContext: CoroutineContext = Dispatchers.Unconfined
    }
}