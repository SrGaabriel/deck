package io.github.deck.core.event.server

import io.github.deck.common.util.GenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.Ban
import io.github.deck.core.entity.Member
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayServerMemberUnbannedEvent

/**
 * Called when a [Member] is pardoned (unbanned)
 */
public data class MemberUnbanEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    public val serverId: GenericId,
    public val ban: Ban
): DeckEvent {
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)
    public val user: StatelessUser get() = ban.user
}

internal val EventService.memberUnbanEvent: EventMapper<GatewayServerMemberUnbannedEvent, MemberUnbanEvent> get() = mapper { client, event ->
    MemberUnbanEvent(
        client = client,
        barebones = event,
        serverId = event.serverId,
        ban = Ban.from(client, event.serverMemberBan)
    )
}