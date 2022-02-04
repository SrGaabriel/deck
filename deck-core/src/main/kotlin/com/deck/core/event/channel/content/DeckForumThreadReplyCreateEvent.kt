package com.deck.core.event.channel.content

import com.deck.common.content.node.decode
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.impl.channel.DeckForumPost
import com.deck.core.event.EventMapper
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessForumChannel
import com.deck.core.stateless.channel.StatelessForumPost
import com.deck.core.stateless.channel.StatelessForumThread
import com.deck.core.util.BlankStatelessForumChannel
import com.deck.core.util.BlankStatelessForumThread
import com.deck.core.util.BlankStatelessTeam
import com.deck.core.util.BlankStatelessUser
import com.deck.gateway.event.type.GatewayTeamChannelContentReplyCreatedEvent

public class DeckForumThreadReplyCreateEvent(
    client: DeckClient,
    gatewayId: Int,
    override val channel: StatelessForumChannel,
    public val thread: StatelessForumThread,
    public val post: StatelessForumPost,
    public val author: StatelessUser,
    public val team: StatelessTeam
): DeckTeamChannelContentReplyCreateEvent(client, gatewayId, channel) {
    public companion object: EventMapper<GatewayTeamChannelContentReplyCreatedEvent, DeckForumThreadReplyCreateEvent> {
        override suspend fun map(
            client: DeckClient,
            event: GatewayTeamChannelContentReplyCreatedEvent
        ): DeckForumThreadReplyCreateEvent {
            val team = BlankStatelessTeam(client, event.teamId)
            val channel = BlankStatelessForumChannel(client, event.channelId.mapToBuiltin(), team)
            val thread = BlankStatelessForumThread(client, event.reply.contentId, team, channel)
            val author = BlankStatelessUser(client, event.reply.createdBy)
            return DeckForumThreadReplyCreateEvent(
                client = client,
                gatewayId = event.gatewayId,
                channel = channel,
                thread = thread,
                post = DeckForumPost(
                    client = client,
                    id = event.reply.id,
                    content = event.reply.message.decode(),
                    thread = thread,
                    team = team,
                    channel = channel,
                    author = author,
                    createdAt = event.reply.createdAt
                ),
                author = author,
                team = team
            )
        }
    }
}