package com.deck.core.event.channel.content

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.event.EventMapper
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.channel.StatelessForumChannel
import com.deck.core.util.BlankStatelessForumChannel
import com.deck.core.util.BlankStatelessTeam
import com.deck.gateway.event.type.GatewayTeamChannelContentDeletedEvent
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive

public class DeckForumThreadDeleteEvent(
    client: DeckClient,
    gatewayId: Int,
    override val channel: StatelessForumChannel,
    public val threadId: IntGenericId,
    public val team: StatelessTeam,
    public val deletedBy: GenericId
): DeckTeamChannelContentDeleteEvent(client, gatewayId, channel) {
    public companion object: EventMapper<GatewayTeamChannelContentDeletedEvent, DeckForumThreadDeleteEvent> {
        override suspend fun map(
            client: DeckClient,
            event: GatewayTeamChannelContentDeletedEvent
        ): DeckForumThreadDeleteEvent {
            val team = BlankStatelessTeam(client, event.teamId)

            return DeckForumThreadDeleteEvent(
                client = client,
                gatewayId = event.gatewayId,
                channel = BlankStatelessForumChannel(client, event.channelId.mapToBuiltin(), team),
                threadId = event.contentId.jsonPrimitive.int,
                team = team,
                deletedBy = event.deletedBy
            )
        }
    }
}