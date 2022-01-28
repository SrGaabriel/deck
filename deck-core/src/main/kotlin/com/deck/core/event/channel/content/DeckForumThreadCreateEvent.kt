package com.deck.core.event.channel.content

import com.deck.common.util.asNullable
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.channel.ForumThread
import com.deck.core.event.EventMapper
import com.deck.core.stateless.channel.StatelessForumChannel
import com.deck.core.util.BlankStatelessForumChannel
import com.deck.gateway.event.type.GatewayTeamChannelContentCreatedEvent

public class DeckForumThreadCreateEvent(
    client: DeckClient,
    gatewayId: Int,
    override val channel: StatelessForumChannel,
    public val thread: ForumThread
): DeckTeamChannelContentCreateEvent(client, gatewayId, channel) {
    public companion object: EventMapper<GatewayTeamChannelContentCreatedEvent, DeckForumThreadCreateEvent> {
        override suspend fun map(
            client: DeckClient,
            event: GatewayTeamChannelContentCreatedEvent
        ): DeckForumThreadCreateEvent {
            val thread = event.thread.asNullable()!!
            val channel = BlankStatelessForumChannel(client, thread.channelId.mapToBuiltin(), thread.teamId)
            return DeckForumThreadCreateEvent(
                client = client,
                gatewayId = event.gatewayId,
                channel = channel,
                thread = client.entityDecoder.decodeForumThread(thread)
            )
        }
    }
}