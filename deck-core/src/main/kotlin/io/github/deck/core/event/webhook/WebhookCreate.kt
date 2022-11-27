package io.github.deck.core.event.webhook

import io.github.deck.core.DeckClient
import io.github.deck.core.entity.Webhook
import io.github.deck.core.entity.impl.DeckWebhook
import io.github.deck.core.event.DeckEvent
import io.github.deck.core.event.EventMapper
import io.github.deck.core.event.EventService
import io.github.deck.core.event.mapper
import io.github.deck.core.stateless.StatelessServer
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.GatewayServerWebhookCreatedEvent

/**
 * Called when a [Webhook] is created
 */
public data class WebhookCreateEvent(
    override val client: DeckClient,
    override val barebones: GatewayEvent,
    val webhook: Webhook
) : DeckEvent {
    public val server: StatelessServer by lazy { webhook.server }
}

internal val EventService.webhookCreate: EventMapper<GatewayServerWebhookCreatedEvent, WebhookCreateEvent> get() = mapper { client, event ->
    WebhookCreateEvent(
        client = client,
        barebones = event,
        webhook = DeckWebhook.from(client, event.webhook)
    )
}