package io.github.deck.core.event.server

import io.github.deck.common.util.GenericId
import io.github.deck.common.util.IntGenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayServerRolesUpdatedEvent

public data class ServerRolesUpdateEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val serverId: GenericId,
    val memberRoles: Map<GenericId, Collection<IntGenericId>>
): DeckEvent

internal val EventService.serverRolesUpdate: EventMapper<GatewayServerRolesUpdatedEvent, ServerRolesUpdateEvent>
    get() = mapper { client, event ->
        ServerRolesUpdateEvent(
            client = client,
            barebones = event,
            serverId = event.serverId,
            memberRoles = event.memberRoleIds.associate { it.userId to it.roleIds }
        )
    }