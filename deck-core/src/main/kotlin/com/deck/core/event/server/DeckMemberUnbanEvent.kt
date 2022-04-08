package com.deck.core.event.server

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.entity.ServerBan
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.core.stateless.StatelessServer
import com.deck.core.stateless.StatelessUser
import com.deck.core.util.BlankStatelessServer
import com.deck.gateway.event.type.GatewayTeamMemberUnbannedEvent

public data class DeckMemberUnbanEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    public val serverId: GenericId,
    public val serverBan: ServerBan
): DeckEvent {
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)
    public val user: StatelessUser get() = serverBan.user

    public companion object: EventMapper<GatewayTeamMemberUnbannedEvent, DeckMemberUnbanEvent> {
        override suspend fun map(
            client: DeckClient,
            event: GatewayTeamMemberUnbannedEvent
        ): DeckMemberUnbanEvent = DeckMemberUnbanEvent(
            client = client,
            gatewayId = event.gatewayId,
            serverId = event.serverId,
            serverBan = client.entityDecoder.decodeBan(event.serverMemberBan)
        )
    }
}