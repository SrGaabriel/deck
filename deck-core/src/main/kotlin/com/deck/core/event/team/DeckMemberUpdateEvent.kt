package com.deck.core.event.team

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.core.event.UserEvent
import com.deck.core.stateless.StatelessUser
import com.deck.core.util.BlankStatelessUser
import com.deck.core.util.MemberPatch
import com.deck.core.util.asDifference
import com.deck.gateway.event.type.GatewayTeamMemberUpdatedEvent

public data class DeckMemberUpdateEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    val userId: GenericId,
    val patch: MemberPatch,
): DeckEvent, UserEvent {
    override val user: StatelessUser get() = BlankStatelessUser(client, userId)

    public companion object: EventMapper<GatewayTeamMemberUpdatedEvent, DeckMemberUpdateEvent> {
        override suspend fun map(client: DeckClient, event: GatewayTeamMemberUpdatedEvent): DeckMemberUpdateEvent {
            return DeckMemberUpdateEvent(
                client = client,
                gatewayId = event.gatewayId,
                userId = event.userId,
                patch = MemberPatch(
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