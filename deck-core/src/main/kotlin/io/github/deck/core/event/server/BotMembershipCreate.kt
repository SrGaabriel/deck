package io.github.deck.core.event.server

import io.github.deck.common.util.GenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.Server
import io.github.deck.core.entity.impl.DeckServer
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessUser
import io.github.deck.core.util.BlankStatelessUser
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayBotServerMembershipCreatedEvent

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