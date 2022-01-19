package com.deck.core.event.channel

import com.deck.common.util.GenericId
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.gateway.event.type.GatewayTeamChannelDeletedEvent
import kotlinx.coroutines.Dispatchers
import java.util.*
import kotlin.coroutines.CoroutineContext

public data class DeckTeamChannelDeleteEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    val channelId: UUID,
    val teamId: GenericId
) : DeckEvent {
    public companion object : EventMapper<GatewayTeamChannelDeletedEvent, DeckTeamChannelDeleteEvent> {
        override suspend fun map(
            client: DeckClient,
            event: GatewayTeamChannelDeletedEvent
        ): DeckTeamChannelDeleteEvent =
            DeckTeamChannelDeleteEvent(
                client,
                event.gatewayId,
                event.channelId.mapToBuiltin(),
                event.teamId
            )

        override val coroutineContext: CoroutineContext = Dispatchers.Unconfined
    }
}