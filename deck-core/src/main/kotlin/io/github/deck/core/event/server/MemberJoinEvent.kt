package io.github.deck.core.event.server

import io.github.deck.common.util.GenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.Member
import io.github.deck.core.entity.impl.DeckMember
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.gateway.event.type.GatewayTeamMemberJoinedEvent

public data class MemberJoinEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    public val serverId: GenericId,
    public val member: Member
): DeckEvent {
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    public companion object: EventMapper<GatewayTeamMemberJoinedEvent, MemberJoinEvent> {
        override suspend fun map(
            client: DeckClient,
            event: GatewayTeamMemberJoinedEvent
        ): MemberJoinEvent = MemberJoinEvent(
            client = client,
            gatewayId = event.gatewayId,
            serverId = event.serverId,
            member = DeckMember.from(client, event.serverId, event.member)
        )
    }
}