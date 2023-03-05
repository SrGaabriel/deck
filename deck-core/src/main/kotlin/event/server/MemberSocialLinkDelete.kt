package io.github.srgaabriel.deck.core.event.server

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.SocialLink
import io.github.srgaabriel.deck.core.event.DeckEvent
import io.github.srgaabriel.deck.core.event.EventMapper
import io.github.srgaabriel.deck.core.event.EventService
import io.github.srgaabriel.deck.core.event.mapper
import io.github.srgaabriel.deck.core.stateless.StatelessMember
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.core.stateless.StatelessUser
import io.github.srgaabriel.deck.core.util.BlankStatelessMember
import io.github.srgaabriel.deck.core.util.BlankStatelessServer
import io.github.srgaabriel.deck.core.util.BlankStatelessUser
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayServerMemberSocialLinkDeletedEvent

public data class MemberSocialLinkDeleteEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val serverId: GenericId,
    val socialLink: SocialLink
): DeckEvent {
    val server: StatelessServer by lazy { BlankStatelessServer(client, serverId) }
    val user: StatelessUser by lazy { BlankStatelessUser(client, socialLink.userId) }
    val member: StatelessMember by lazy { BlankStatelessMember(client, socialLink.userId, serverId) }
}

internal val EventService.memberSocialLinkDelete: EventMapper<GatewayServerMemberSocialLinkDeletedEvent, MemberSocialLinkDeleteEvent>
    get() = mapper { client, event ->
        MemberSocialLinkDeleteEvent(
        client = client,
        barebones = event,
        serverId = event.serverId,
        socialLink = SocialLink.from(client, event.socialLink)
    )
}