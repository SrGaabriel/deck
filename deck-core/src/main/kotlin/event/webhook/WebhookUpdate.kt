package io.github.srgaabriel.deck.core.event.webhook

import io.github.srgaabriel.deck.core.DeckClient
import io.github.srgaabriel.deck.core.entity.Webhook
import io.github.srgaabriel.deck.core.entity.impl.DeckWebhook
import io.github.srgaabriel.deck.core.event.DeckEvent
import io.github.srgaabriel.deck.core.event.EventMapper
import io.github.srgaabriel.deck.core.event.EventService
import io.github.srgaabriel.deck.core.event.mapper
import io.github.srgaabriel.deck.core.stateless.StatelessServer
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import io.github.srgaabriel.deck.gateway.event.type.GatewayServerWebhookUpdatedEvent

/**
 * Called when a [Webhook] is updated
 */
public data class WebhookUpdateEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val webhook: Webhook
) : DeckEvent {
    public val server: StatelessServer by lazy { webhook.server }
}

internal val EventService.webhookUpdate: EventMapper<GatewayServerWebhookUpdatedEvent, WebhookUpdateEvent> get() = mapper { client, event ->
    WebhookUpdateEvent(
        client = client,
        barebones = event,
        webhook = DeckWebhook.from(client, event.webhook)
    )
}