package com.deck.core.event.channel.content

import com.deck.common.util.GenericId
import com.deck.common.util.IntGenericId
import com.deck.common.util.mapToBuiltin
import com.deck.core.DeckClient
import com.deck.core.event.EventMapper
import com.deck.core.stateless.StatelessUser
import com.deck.core.stateless.channel.StatelessForumChannel
import com.deck.core.util.BlankStatelessForumChannel
import com.deck.core.util.BlankStatelessTeam
import com.deck.core.util.BlankStatelessUser
import com.deck.gateway.event.type.GatewayTeamChannelContentDeletedEvent
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive
import java.util.*

public class DeckForumThreadDeleteEvent(
    client: DeckClient,
    gatewayId: Int,
    channelId: UUID,
    public val threadId: IntGenericId,
    public val teamId: GenericId,
    public val deletedBy: StatelessUser
): DeckTeamChannelContentDeleteEvent(client, gatewayId, channelId) {
    override val channel: StatelessForumChannel get() = BlankStatelessForumChannel(client, channelId, teamId)

    public companion object: EventMapper<GatewayTeamChannelContentDeletedEvent, DeckForumThreadDeleteEvent> {
        override suspend fun map(
            client: DeckClient,
            event: GatewayTeamChannelContentDeletedEvent
        ): DeckForumThreadDeleteEvent {
            val team = BlankStatelessTeam(client, event.teamId)
            return DeckForumThreadDeleteEvent(
                client = client,
                gatewayId = event.gatewayId,
                channelId = event.channelId.mapToBuiltin(),
                threadId = event.contentId.jsonPrimitive.int,
                teamId = event.teamId,
                deletedBy = BlankStatelessUser(client, event.deletedBy)
            )
        }
    }
}