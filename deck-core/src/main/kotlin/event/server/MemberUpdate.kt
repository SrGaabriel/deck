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
import io.github.srgaabriel.deck.core.util.Patch
import io.github.srgaabriel.deck.core.util.asPatch
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayServerMemberUpdatedEvent

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