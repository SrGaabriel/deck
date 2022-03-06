package com.deck.core.event.channel.content

import com.deck.common.entity.RawChannelContentType
import com.deck.core.DeckClient
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.core.stateless.generic.GenericStatelessChannel
import com.deck.gateway.event.type.GatewayTeamChannelContentReplyCreatedEvent
import java.util.*

public abstract class DeckTeamChannelContentReplyCreateEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    public open val channelId: UUID
): DeckEvent {
    public abstract val channel: GenericStatelessChannel

    public companion object: EventMapper<GatewayTeamChannelContentReplyCreatedEvent, DeckTeamChannelContentReplyCreateEvent> {
        override suspend fun map(
            client: DeckClient,
            event: GatewayTeamChannelContentReplyCreatedEvent,
        ): DeckTeamChannelContentReplyCreateEvent? {
            return when (event.contentType) {
                RawChannelContentType.Forum -> DeckForumThreadReplyCreateEvent.map(client, event)
                else -> null
            }
        }
    }
}