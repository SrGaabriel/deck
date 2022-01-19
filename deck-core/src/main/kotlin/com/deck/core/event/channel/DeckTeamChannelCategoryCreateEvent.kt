package com.deck.core.event.channel

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.entity.TeamChannelCategory
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.gateway.event.type.GatewayTeamChannelCategoryCreatedEvent
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

public data class DeckTeamChannelCategoryCreateEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    val teamId: GenericId,
    val category: TeamChannelCategory
) : DeckEvent {
    public companion object :
        EventMapper<GatewayTeamChannelCategoryCreatedEvent, DeckTeamChannelCategoryCreateEvent> {
        override suspend fun map(
            client: DeckClient,
            event: GatewayTeamChannelCategoryCreatedEvent
        ): DeckTeamChannelCategoryCreateEvent =
            DeckTeamChannelCategoryCreateEvent(
                client,
                event.gatewayId,
                event.teamId,
                client.entityStrategizer.decodeCategory(event.category)
            )

        override val coroutineContext: CoroutineContext = Dispatchers.Unconfined
    }
}