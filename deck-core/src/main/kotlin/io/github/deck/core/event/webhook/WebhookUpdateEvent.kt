package io.github.deck.core.event.webhook

import io.github.deck.common.util.GenericId
import io.github.deck.core.DeckClient
import io.github.deck.core.entity.Webhook
import io.github.deck.core.entity.impl.DeckWebhook
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.core.util.BlankStatelessServer
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayServerWebhookUpdatedEvent

/**
 * Called when a [Webhook] is updated
 */
public data class WebhookUpdateEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val webhook: Webhook,
    val serverId: GenericId
) : DeckEvent {
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)
}

internal val EventService.webhookUpdateEvent: EventMapper<GatewayServerWebhookUpdatedEvent, WebhookUpdateEvent> get() = mapper { client, event ->
    WebhookUpdateEvent(
        client = client,
        barebones = event,
        webhook = DeckWebhook.from(client, event.webhook),
        serverId = event.serverId
    )
}