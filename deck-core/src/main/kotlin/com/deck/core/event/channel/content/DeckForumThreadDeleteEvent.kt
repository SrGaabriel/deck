package com.deck.core.event.channel.content

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.event.EventMapper
import com.deck.core.stateless.channel.StatelessForumChannel
import com.deck.core.util.BlankStatelessForumChannel
import com.deck.gateway.event.type.GatewayTeamChannelContentDeletedEvent
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive

public class DeckForumThreadDeleteEvent(
    client: DeckClient,
    gatewayId: Int,
    override val channel: StatelessForumChannel,
    public val threadId: IntGenericId,
    public val teamId: GenericId,
    public val deletedBy: GenericId
): DeckTeamChannelContentDeleteEvent(client, gatewayId, channel) {
    public companion object: EventMapper<GatewayTeamChannelContentDeletedEvent, DeckForumThreadDeleteEvent> {
        override suspend fun map(
            client: DeckClient,
            event: GatewayTeamChannelContentDeletedEvent
        ): DeckForumThreadDeleteEvent =
            DeckForumThreadDeleteEvent(
                client = client,
                gatewayId = event.gatewayId,
                channel = BlankStatelessForumChannel(client, event.channelId.mapToBuiltin(), event.teamId),
                threadId = event.contentId.jsonPrimitive.int,
                teamId = event.teamId,
                deletedBy = event.deletedBy
            )
    }
}