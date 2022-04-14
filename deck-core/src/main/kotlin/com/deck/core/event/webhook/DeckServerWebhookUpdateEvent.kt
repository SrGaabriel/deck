package com.deck.core.event.webhook

import com.deck.common.util.GenericId
import com.deck.core.DeckClient
import com.deck.core.entity.Webhook
import com.deck.core.entity.impl.DeckWebhook
import com.deck.core.event.DeckEvent
import com.deck.core.event.EventMapper
import com.deck.core.stateless.StatelessServer
import com.deck.core.util.BlankStatelessServer
import com.deck.gateway.event.type.GatewayServerWebhookUpdatedEvent

public data class DeckServerWebhookUpdateEvent(
    override val client: DeckClient,
    override val gatewayId: Int,
    val webhook: Webhook,
    val serverId: GenericId
) : DeckEvent {
    public val server: StatelessServer get() = BlankStatelessServer(client, serverId)

    public companion object: EventMapper<GatewayServerWebhookUpdatedEvent, DeckServerWebhookUpdateEvent> {
        override suspend fun map(
            client: DeckClient,
            event: GatewayServerWebhookUpdatedEvent,
        ): DeckServerWebhookUpdateEvent = DeckServerWebhookUpdateEvent(
            client = client,
            gatewayId = event.gatewayId,
            webhook = DeckWebhook.from(client, event.webhook),
            serverId = event.serverId
        )
    }
}