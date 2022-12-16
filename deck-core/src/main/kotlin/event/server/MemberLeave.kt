package io.github.srgaabriel.deck.core.event.server

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.Member
import io.github.srgaabriel.deck.core.event.DeckEvent
import io.github.srgaabriel.deck.core.event.EventMapper
import io.github.srgaabriel.deck.core.event.EventService
import io.github.srgaabriel.deck.core.event.mapper
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.StatelessUser
import io.github.srgaabriel.deck.core.util.BlankStatelessServer
import io.github.srgaabriel.deck.core.util.BlankStatelessUser
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayServerMemberRemovedEvent

/**
 * Called when a [Member] leaves a server
 * (even if it's because of a kick or a ban)
 */
public data class MemberLeaveEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    public val serverId: GenericId,
    public val userId: GenericId,
    public val isKick: Boolean,
    public val isBan: Boolean
): DeckEvent {
    public val server: StatelessServer by lazy { BlankStatelessServer(client, serverId) }
    public val user: StatelessUser by lazy { BlankStatelessUser(client, userId) }
}

internal val EventService.memberLeave: EventMapper<GatewayServerMemberRemovedEvent, MemberLeaveEvent> get() = mapper { client, event ->
    MemberLeaveEvent(
        client = client,
        barebones = event,
        serverId = event.serverId,
        userId = event.userId,
        isKick = event.isKick,
        isBan = event.isBan
    )
}