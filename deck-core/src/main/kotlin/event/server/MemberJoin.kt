package io.github.srgaabriel.deck.core.event.server

import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.Member
import io.github.srgaabriel.deck.core.entity.impl.DeckMember
import io.github.srgaabriel.deck.core.event.DeckEvent
import io.github.srgaabriel.deck.core.event.EventMapper
import io.github.srgaabriel.deck.core.event.EventService
import io.github.srgaabriel.deck.core.event.mapper
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayServerMemberJoinedEvent

/**
 * Called when a new [Member] joins a server
 */
public data class MemberJoinEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    public val member: Member
): DeckEvent {
    public val server: StatelessServer by lazy { member.server }
}

internal val EventService.memberJoin: EventMapper<GatewayServerMemberJoinedEvent, MemberJoinEvent> get() = mapper { client, event ->
    MemberJoinEvent(
        client = client,
        barebones = event,
        member = DeckMember.from(client, event.serverId, event.member)
    )
}