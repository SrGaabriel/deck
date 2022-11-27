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
import io.github.deck.core.util.Patch
import io.github.deck.core.util.asPatch
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayServerMemberUpdatedEvent

/**
 * Called when a [Member]'s attributes are updated
 */
public data class MemberUpdateEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val serverId: GenericId,
    val userId: GenericId,
    val memberData: MemberData
): DeckEvent {
    public val user: StatelessUser by lazy { BlankStatelessUser(client, userId) }
    public val server: StatelessServer by lazy { BlankStatelessServer(client, serverId) }

    public data class MemberData(
        val nickname: Patch<String>
    )
}

internal val EventService.memberUpdate: EventMapper<GatewayServerMemberUpdatedEvent, MemberUpdateEvent> get() = mapper { client, event ->
    MemberUpdateEvent(
        client = client,
        barebones = event,
        serverId = event.serverId,
        userId = event.userInfo.id,
        memberData = MemberUpdateEvent.MemberData(
            nickname = event.userInfo.nickname.asPatch()
        )
    )
}