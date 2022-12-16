package io.github.srgaabriel.deck.core.event.server

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.Ban
import io.github.srgaabriel.deck.core.entity.Member
import io.github.srgaabriel.deck.core.event.DeckEvent
import io.github.srgaabriel.deck.core.event.EventMapper
import io.github.srgaabriel.deck.core.event.EventService
import io.github.srgaabriel.deck.core.event.mapper
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.StatelessUser
import io.github.srgaabriel.deck.core.util.BlankStatelessServer
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayServerMemberBannedEvent

/**
 * Called when a [Member] is banned from a server
 */
public data class MemberBanEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    public val serverId: GenericId,
    public val ban: Ban
): DeckEvent {
    public val server: StatelessServer by lazy { BlankStatelessServer(client, serverId) }
    public val user: StatelessUser by lazy { ban.user }
    public val author: StatelessUser by lazy { ban.author }
}

internal val EventService.memberBan: EventMapper<GatewayServerMemberBannedEvent, MemberBanEvent> get() = mapper { client, event ->
    MemberBanEvent(
        client = client,
        barebones = event,
        serverId = event.serverId,
        ban = Ban.from(client, event.serverMemberBan)
    )
}