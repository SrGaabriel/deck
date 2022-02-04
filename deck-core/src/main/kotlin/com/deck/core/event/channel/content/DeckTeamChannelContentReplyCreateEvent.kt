package com.deck.core.event.channel.content

import com.deck.common.entity.RawChannelContentType
import com.deck.core.DeckClient
import com.deck.core.entity.channel.Channel
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.core.stateless.StatelessEntity
import com.deck.gateway.event.type.GatewayTeamChannelContentReplyCreatedEvent

public abstract class DeckTeamChannelContentReplyCreateEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    public open val channel: StatelessEntity<out Channel>
): DeckEvent {
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