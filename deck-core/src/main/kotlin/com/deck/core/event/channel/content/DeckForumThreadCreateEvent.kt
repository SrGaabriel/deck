package com.deck.core.event.channel.content

import com.deck.common.util.GenericId
import com.deck.common.util.getValue
import com.deck.core.DeckClient
import com.deck.core.entity.channel.ForumThread
import com.deck.core.event.EventMapper
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessForumChannel
import com.deck.core.util.BlankStatelessForumChannel
import com.deck.core.util.BlankStatelessTeam
import com.deck.core.util.BlankStatelessUser
import com.deck.gateway.event.type.GatewayTeamChannelContentCreatedEvent
import java.util.*

public class DeckForumThreadCreateEvent(
    client: DeckClient,
    gatewayId: Int,
    channelId: UUID,
    public val thread: ForumThread,
    public val teamId: GenericId,
    public val authorId: GenericId
): DeckTeamChannelContentCreateEvent(client, gatewayId, channelId) {
    override val channel: StatelessForumChannel get() = BlankStatelessForumChannel(client, channelId, teamId)
    public val team: StatelessTeam get() = BlankStatelessTeam(client, teamId)
    public val author: StatelessUser get() = BlankStatelessUser(client, authorId)

    public companion object: EventMapper<GatewayTeamChannelContentCreatedEvent, DeckForumThreadCreateEvent> {
        override suspend fun map(
            client: DeckClient,
            event: GatewayTeamChannelContentCreatedEvent
        ): DeckForumThreadCreateEvent {
            val thread = client.entityDecoder.decodeForumThread(event.thread.getValue())
            return DeckForumThreadCreateEvent(
                client = client,
                gatewayId = event.gatewayId,
                channelId = thread.channelId,
                thread = thread,
                teamId = thread.teamId,
                authorId = thread.authorId
            )
        }
    }
}