package com.deck.core.event.channel.content

import com.deck.common.util.asNullable
import com.deck.core.DeckClient
import com.deck.core.entity.channel.ForumThread
import com.deck.core.event.EventMapper
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessForumChannel
import com.deck.gateway.event.type.GatewayTeamChannelContentCreatedEvent

public class DeckForumThreadCreateEvent(
    client: DeckClient,
    gatewayId: Int,
    override val channel: StatelessForumChannel,
    public val thread: ForumThread,
    public val team: StatelessTeam,
    public val author: StatelessUser
): DeckTeamChannelContentCreateEvent(client, gatewayId, channel) {
    public companion object: EventMapper<GatewayTeamChannelContentCreatedEvent, DeckForumThreadCreateEvent> {
        override suspend fun map(
            client: DeckClient,
            event: GatewayTeamChannelContentCreatedEvent
        ): DeckForumThreadCreateEvent {
            val thread = client.entityDecoder.decodeForumThread(event.thread.asNullable()!!)
            return DeckForumThreadCreateEvent(
                client = client,
                gatewayId = event.gatewayId,
                channel = thread.channel,
                thread = thread,
                team = thread.team,
                author = thread.author
            )
        }
    }
}