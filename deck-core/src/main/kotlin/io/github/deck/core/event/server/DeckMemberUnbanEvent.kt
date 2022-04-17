package io.github.deck.core.event.server

import io.github.deck.common.util.GenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.ServerBan
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.gateway.event.type.GatewayTeamMemberUnbannedEvent

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
            serverBan = ServerBan.from(client, event.serverMemberBan)
        )
    }
}