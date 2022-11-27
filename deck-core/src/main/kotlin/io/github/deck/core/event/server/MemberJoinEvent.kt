package io.github.deck.core.event.server

import io.github.deck.common.util.GenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.Member
import io.github.deck.core.entity.impl.DeckMember
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayServerMemberJoinedEvent

/**
 * Called when a new [Member] joins a server
 */
public data class MemberJoinEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    public val serverId: GenericId,
    public val member: Member
): DeckEvent {
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)
}

internal val EventService.memberJoinEvent: EventMapper<GatewayServerMemberJoinedEvent, MemberJoinEvent> get() = mapper { client, event ->
    MemberJoinEvent(
        client = client,
        barebones = event,
        serverId = event.serverId,
        member = DeckMember.from(client, event.serverId, event.member)
    )
}