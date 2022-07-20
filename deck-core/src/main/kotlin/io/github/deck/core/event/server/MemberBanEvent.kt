package io.github.deck.core.event.server

import io.github.deck.common.util.GenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.ServerBan
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.gateway.event.Payload
import io.github.deck.gateway.event.type.GatewayTeamMemberBannedEvent

public data class MemberBanEvent(
    override val client: DeckClient,
    override val payload: Payload,
    public val serverId: GenericId,
    public val serverBan: ServerBan
): DeckEvent {
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)
    public val user: StatelessUser get() = serverBan.user
    public val author: StatelessUser get() = serverBan.author
}

public val EventService.memberBanEvent: EventMapper<GatewayTeamMemberBannedEvent, MemberBanEvent> get() = mapper { client, event ->
    MemberBanEvent(
        client = client,
        payload = event.payload,
        serverId = event.serverId,
        serverBan = ServerBan.from(client, event.serverMemberBan)
    )
}