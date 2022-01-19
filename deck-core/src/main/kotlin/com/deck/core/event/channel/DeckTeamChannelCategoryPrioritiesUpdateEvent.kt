package com.deck.core.event.channel

import com.deck.common.util.IntGenericId
import com.deck.core.DeckClient
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.gateway.event.type.GatewayTeamChannelCategoryPrioritiesUpdatedEvent
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

public data class DeckTeamChannelCategoryPrioritiesUpdateEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    val orderedChannelCategoryIds: List<IntGenericId>
) : DeckEvent {
    public companion object :
        EventMapper<GatewayTeamChannelCategoryPrioritiesUpdatedEvent, DeckTeamChannelCategoryPrioritiesUpdateEvent> {
        override suspend fun map(
            client: DeckClient,
            event: GatewayTeamChannelCategoryPrioritiesUpdatedEvent
        ): DeckTeamChannelCategoryPrioritiesUpdateEvent =
            DeckTeamChannelCategoryPrioritiesUpdateEvent(
                client,
                event.gatewayId,
                event.orderedChannelCategoryIds
            )

        override val coroutineContext: CoroutineContext = Dispatchers.Unconfined
    }
}