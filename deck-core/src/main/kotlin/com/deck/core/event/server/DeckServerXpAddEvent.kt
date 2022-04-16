package com.deck.core.event.server

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.core.stateless.StatelessServer
import com.deck.core.stateless.StatelessUser
import com.deck.core.util.BlankStatelessServer
import com.deck.core.util.BlankStatelessUser
import com.deck.gateway.event.type.GatewayServerXpAddedEvent

public data class DeckServerXpAddEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    public val userIds: List<GenericId>,
    public val serverId: GenericId,
    public val amount: Int
) : DeckEvent {
    public val users: List<StatelessUser> get() = userIds.map { BlankStatelessUser(client, it) }
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    public companion object: EventMapper<GatewayServerXpAddedEvent, DeckServerXpAddEvent> {
        override suspend fun map(client: DeckClient, event: GatewayServerXpAddedEvent): DeckServerXpAddEvent = DeckServerXpAddEvent(
            client = client,
            gatewayId = event.gatewayId,
            userIds = event.userIds,
            serverId = event.serverId,
            amount = event.amount
        )
    }
}