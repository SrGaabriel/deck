package com.deck.core.event.server

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.core.stateless.StatelessServer
import com.deck.core.stateless.StatelessUser
import com.deck.core.util.BlankStatelessServer
import com.deck.core.util.BlankStatelessUser
import com.deck.gateway.event.type.GatewayTeamMemberRemovedEvent

public data class DeckMemberLeaveEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    public val serverId: GenericId,
    public val userId: GenericId,
    public val isKick: Boolean,
    public val isBan: Boolean
): DeckEvent {
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)
    public val user: StatelessUser get() = BlankStatelessUser(client, userId)

    public companion object: EventMapper<GatewayTeamMemberRemovedEvent, DeckMemberLeaveEvent> {
        override suspend fun map(
            client: DeckClient,
            event: GatewayTeamMemberRemovedEvent
        ): DeckMemberLeaveEvent = DeckMemberLeaveEvent(
            client = client,
            gatewayId = event.gatewayId,
            serverId = event.serverId,
            userId = event.userId,
            isKick = event.isKick,
            isBan = event.isBan
        )
    }
}