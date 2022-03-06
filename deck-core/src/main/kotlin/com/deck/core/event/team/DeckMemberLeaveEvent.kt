package com.deck.core.event.team

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.entity.Member
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.core.stateless.StatelessTeam
import com.deck.core.stateless.StatelessUser
import com.deck.core.util.BlankStatelessTeam
import com.deck.core.util.BlankStatelessUser
import com.deck.gateway.event.type.GatewayTeamMemberRemovedEvent

public data class DeckMemberLeaveEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    val userId: GenericId,
    val teamId: GenericId,
    val oldMember: Member?
): DeckEvent {
    val user: StatelessUser get() = BlankStatelessUser(client, userId)
    val team: StatelessTeam get() = BlankStatelessTeam(client, teamId)

    public companion object: EventMapper<GatewayTeamMemberRemovedEvent, DeckMemberLeaveEvent> {
        override suspend fun map(client: DeckClient, event: GatewayTeamMemberRemovedEvent): DeckMemberLeaveEvent= DeckMemberLeaveEvent(
            client = client,
            gatewayId = event.gatewayId,
            userId = event.userId,
            teamId = event.teamId,
            oldMember = client.cache.retrieveMember(id = event.userId, teamId = event.teamId)
        )
    }
}