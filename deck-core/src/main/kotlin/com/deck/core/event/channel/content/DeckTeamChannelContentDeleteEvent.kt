package com.deck.core.event.channel.content

import com.deck.common.entity.RawChannelContentType
import com.deck.core.DeckClient
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.core.stateless.generic.GenericStatelessChannel
import com.deck.gateway.event.type.GatewayTeamChannelContentDeletedEvent
import java.util.*

public abstract class DeckTeamChannelContentDeleteEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    public val channelId: UUID
): DeckEvent {
    public abstract val channel: GenericStatelessChannel

    public companion object: EventMapper<GatewayTeamChannelContentDeletedEvent, DeckTeamChannelContentDeleteEvent> {
        override suspend fun map(
            client: DeckClient,
            event: GatewayTeamChannelContentDeletedEvent,
        ): DeckTeamChannelContentDeleteEvent? {
            return when (event.contentType) {
                RawChannelContentType.Forum -> DeckForumThreadDeleteEvent.map(client, event)
                else -> null
            }
        }
    }
}