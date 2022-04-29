package io.github.deck.core.event.server

import io.github.deck.common.util.GenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.core.util.BlankStatelessUser
import io.github.deck.gateway.event.type.GatewayTeamMemberRemovedEvent

public data class MemberLeaveEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    public val serverId: GenericId,
    public val userId: GenericId,
    public val isKick: Boolean,
    public val isBan: Boolean
): DeckEvent {
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)
    public val user: StatelessUser get() = BlankStatelessUser(client, userId)

    public companion object: EventMapper<GatewayTeamMemberRemovedEvent, MemberLeaveEvent> {
        override suspend fun map(
            client: DeckClient,
            event: GatewayTeamMemberRemovedEvent
        ): MemberLeaveEvent = MemberLeaveEvent(
            client = client,
            gatewayId = event.gatewayId,
            serverId = event.serverId,
            userId = event.userId,
            isKick = event.isKick,
            isBan = event.isBan
        )
    }
}