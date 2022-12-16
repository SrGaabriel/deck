package io.github.srgaabriel.deck.core.event.server

import io.github.srgaabriel.deck.common.util.GenericId
import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.Server
import io.github.srgaabriel.deck.core.entity.impl.DeckServer
import io.github.srgaabriel.deck.core.event.DeckEvent
import io.github.srgaabriel.deck.core.event.EventMapper
import io.github.srgaabriel.deck.core.event.EventService
import io.github.srgaabriel.deck.core.event.mapper
import io.github.srgaabriel.deck.core.stateless.StatelessUser
import io.github.srgaabriel.deck.core.util.BlankStatelessUser
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayBotServerMembershipDeletedEvent

/**
 * Emitted when the bot is removed from a server
 */
public data class BotMembershipDeleteEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val server: Server,
    val deletedBy: GenericId
): DeckEvent {
    val deleter: StatelessUser by lazy { BlankStatelessUser(client, deletedBy) }
}

internal val EventService.botMembershipDelete: EventMapper<GatewayBotServerMembershipDeletedEvent, BotMembershipDeleteEvent> get() =
    mapper { client, event ->
        BotMembershipDeleteEvent(
            client = client,
            barebones = event,
            server = DeckServer.from(client, event.server),
            deletedBy = event.deletedBy
        )
    }