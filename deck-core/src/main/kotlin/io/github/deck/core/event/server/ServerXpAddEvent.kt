package io.github.deck.core.event.server

import io.github.deck.common.util.GenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.core.util.BlankStatelessUser
import io.github.deck.gateway.event.type.GatewayServerXpAddedEvent

public data class ServerXpAddEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    public val userIds: List<GenericId>,
    public val serverId: GenericId,
    public val amount: Int
) : DeckEvent {
    public val users: List<StatelessUser> get() = userIds.map { BlankStatelessUser(client, it) }
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)
}

public val EventService.serverXpAddEvent: EventMapper<GatewayServerXpAddedEvent, ServerXpAddEvent> get() = mapper { client, event ->
    ServerXpAddEvent(
        client = client,
        gatewayId = event.gatewayId,
        userIds = event.userIds,
        serverId = event.serverId,
        amount = event.amount
    )
}