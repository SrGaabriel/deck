package com.deck.core.event.team

import com.deck.core.DeckClient
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.core.event.UserEvent
import com.deck.core.stateless.StatelessUser
import com.deck.core.util.BlankStatelessUser
import com.deck.core.util.Patch
import com.deck.core.util.asDifference
import com.deck.gateway.event.type.GatewayTeamMemberUpdatedEvent

public data class DeckMemberUpdateEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    override val user: StatelessUser,
    val patch: Patch.Member,
): DeckEvent, UserEvent {
    public companion object: EventMapper<GatewayTeamMemberUpdatedEvent, DeckMemberUpdateEvent> {
        override suspend fun map(client: DeckClient, event: GatewayTeamMemberUpdatedEvent): DeckMemberUpdateEvent {
            return DeckMemberUpdateEvent(
                client = client,
                gatewayId = event.gatewayId,
                user = BlankStatelessUser(client, event.userId),
                patch = Patch.Member(
                    name = event.userInfo.name.asDifference(),
                    nickname = event.userInfo.nickname.asDifference(),
                    biography = event.userInfo.biography.asDifference(),
                    tagline = event.userInfo.tagline.asDifference(),
                    avatar = event.userInfo.profilePicture.asDifference(),
                    banner = event.userInfo.profileBanner.asDifference()
                )
            )
        }
    }
}