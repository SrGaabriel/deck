package io.github.deck.core.event.server

import io.github.deck.common.util.GenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.util.BlankStatelessUser
import io.github.deck.core.util.Patch
import io.github.deck.core.util.asPatch
import io.github.deck.gateway.event.Payload
import io.github.deck.gateway.event.type.GatewayTeamMemberUpdatedEvent

public data class MemberUpdateEvent(
    override val client: DeckClient,
    override val payload: Payload,
    val serverId: GenericId,
    val userId: GenericId,
    val memberData: MemberData
): DeckEvent {
    public val user: StatelessUser get() = BlankStatelessUser(client, userId)

    public data class MemberData(
        val nickname: Patch<String>
    )
}

public val EventService.memberUpdateEvent: EventMapper<GatewayTeamMemberUpdatedEvent, MemberUpdateEvent> get() = mapper { client, event ->
    MemberUpdateEvent(
        client = client,
        payload = event.payload,
        serverId = event.serverId,
        userId = event.userInfo.id,
        memberData = MemberUpdateEvent.MemberData(
            nickname = event.userInfo.nickname.asPatch()
        )
    )
}