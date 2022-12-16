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
import io.github.srgaabriel.deck.gateway.event.type.GatewayBotServerMembershipCreatedEvent

/**
 * Emitted when the bot is added to a server
 */
public data class BotMembershipCreateEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val server: Server,
    val createdBy: GenericId
): DeckEvent {
    val creator: StatelessUser by lazy { BlankStatelessUser(client, createdBy) }
}

internal val EventService.botMembershipCreated: EventMapper<GatewayBotServerMembershipCreatedEvent, BotMembershipCreateEvent> get() =
    mapper { client, event ->
        BotMembershipCreateEvent(
            client = client,
            barebones = event,
            server = DeckServer.from(client, event.server),
            createdBy = event.createdBy
        )
    }