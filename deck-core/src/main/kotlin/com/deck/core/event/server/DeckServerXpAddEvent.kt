package com.deck.core.event.server

import com.deck.core.DeckClient
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.core.stateless.StatelessServer
import com.deck.core.stateless.StatelessUser
import com.deck.core.util.BlankStatelessServer
import com.deck.core.util.BlankStatelessUser
import com.deck.gateway.event.type.GatewayServerXpAddeedEvent

public data class DeckServerXpAddEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    public val users: List<StatelessUser>,
    public val server: StatelessServer,
    public val amount: Int
) : DeckEvent {
    public companion object: EventMapper<GatewayServerXpAddeedEvent, DeckServerXpAddEvent> {
        override suspend fun map(client: DeckClient, event: GatewayServerXpAddeedEvent): DeckServerXpAddEvent = DeckServerXpAddEvent(
            client = client,
            gatewayId = event.gatewayId,
            users = event.userIds.map { BlankStatelessUser(client, it) },
            server = BlankStatelessServer(client, event.serverId),
            amount = event.amount
        )
    }
}