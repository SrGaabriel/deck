package com.deck.core.event.server

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.entity.Member
import com.deck.core.entity.impl.DeckMember
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.core.stateless.StatelessServer
import com.deck.core.util.BlankStatelessServer
import com.deck.gateway.event.type.GatewayTeamMemberJoinedEvent

public data class DeckMemberJoinEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    public val serverId: GenericId,
    public val member: Member
): DeckEvent {
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    public companion object: EventMapper<GatewayTeamMemberJoinedEvent, DeckMemberJoinEvent> {
        override suspend fun map(
            client: DeckClient,
            event: GatewayTeamMemberJoinedEvent
        ): DeckMemberJoinEvent = DeckMemberJoinEvent(
            client = client,
            gatewayId = event.gatewayId,
            serverId = event.serverId,
            member = DeckMember.from(client, event.serverId, event.member)
        )
    }
}