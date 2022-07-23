package io.github.deck.core.event.server

import io.github.deck.common.util.GenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.Member
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.core.util.BlankStatelessUser
import io.github.deck.gateway.event.Payload
import io.github.deck.gateway.event.type.GatewayTeamMemberRemovedEvent

/**
 * Called when a [Member] leaves a server
 * (even if it's because of a kick or a ban)
 */
public data class MemberLeaveEvent(
    override val client: DeckClient,
    override val payload: Payload,
    public val serverId: GenericId,
    public val userId: GenericId,
    public val isKick: Boolean,
    public val isBan: Boolean
): DeckEvent {
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)
    public val user: StatelessUser get() = BlankStatelessUser(client, userId)
}

internal val EventService.memberLeaveEvent: EventMapper<GatewayTeamMemberRemovedEvent, MemberLeaveEvent> get() = mapper { client, event ->
    MemberLeaveEvent(
        client = client,
        payload = event.payload,
        serverId = event.serverId,
        userId = event.userId,
        isKick = event.isKick,
        isBan = event.isBan
    )
}