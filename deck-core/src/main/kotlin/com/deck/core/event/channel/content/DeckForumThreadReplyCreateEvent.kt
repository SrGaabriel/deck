package com.deck.core.event.channel.content

import com.deck.common.content.node.decode
import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.entity.channel.ForumPost
import com.deck.core.entity.impl.channel.DeckForumPost
import com.deck.core.event.EventMapper
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessForumChannel
import com.deck.core.stateless.channel.StatelessForumThread
import com.deck.core.util.BlankStatelessForumChannel
import com.deck.core.util.BlankStatelessForumThread
import com.deck.core.util.BlankStatelessTeam
import com.deck.core.util.BlankStatelessUser
import com.deck.gateway.event.type.GatewayTeamChannelContentReplyCreatedEvent
import java.util.*

public class DeckForumThreadReplyCreateEvent(
    client: DeckClient,
    gatewayId: Int,
    channelId: UUID,
    public val threadId: IntGenericId,
    public val post: ForumPost,
    public val authorId: GenericId,
    public val teamId: GenericId
): DeckTeamChannelContentReplyCreateEvent(client, gatewayId, channelId) {
    override val channel: StatelessForumChannel get() = BlankStatelessForumChannel(client, channelId, teamId)
    public val thread: StatelessForumThread get() = BlankStatelessForumThread(client, threadId, teamId, channelId)
    public val author: StatelessUser get() = BlankStatelessUser(client, authorId)
    public val team: StatelessTeam get() = BlankStatelessTeam(client, teamId)

    public companion object: EventMapper<GatewayTeamChannelContentReplyCreatedEvent, DeckForumThreadReplyCreateEvent> {
        override suspend fun map(
            client: DeckClient,
            event: GatewayTeamChannelContentReplyCreatedEvent
        ): DeckForumThreadReplyCreateEvent {
            val channelId = event.channelId.mapToBuiltin()
            return DeckForumThreadReplyCreateEvent(
                client = client,
                gatewayId = event.gatewayId,
                channelId = channelId,
                threadId = event.reply.contentId,
                post = DeckForumPost(
                    client = client,
                    id = event.reply.id,
                    content = event.reply.message.decode(),
                    threadId = event.reply.contentId,
                    teamId = event.teamId,
                    channelId = event.channelId.mapToBuiltin(),
                    authorId = event.createdBy,
                    createdAt = event.reply.createdAt
                ),
                authorId = event.createdBy,
                teamId = event.teamId
            )
        }
    }
}