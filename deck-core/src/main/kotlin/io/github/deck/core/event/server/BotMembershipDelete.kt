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
import io.github.deck.gateway.event.type.GatewayBotServerMembershipDeletedEvent

/**
 * Emitted when the bot is removed from a server
 */
public data class BotMembershipDeleteEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val server: Server,
    val deletedBy: GenericId
): DeckEvent {
    val deleter: StatelessUser get() = BlankStatelessUser(client, deletedBy)
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