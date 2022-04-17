package io.github.deck.core.event

import io.github.deck.core.DeckClient
import io.github.deck.core.event.message.DeckMessageCreateEvent
import io.github.deck.core.event.message.DeckMessageDeleteEvent
import io.github.deck.core.event.message.DeckMessageUpdateEvent
import io.github.deck.core.event.server.*
import io.github.deck.core.event.user.DeckHelloEvent
import io.github.deck.core.event.webhook.DeckServerWebhookCreateEvent
import io.github.deck.core.event.webhook.DeckServerWebhookUpdateEvent
import io.github.deck.core.util.WrappedEventSupplier
import io.github.deck.core.util.WrappedEventSupplierData
import io.github.deck.gateway.event.GatewayEvent
import io.github.deck.gateway.event.type.*
import io.github.deck.gateway.util.on
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

public interface DeckEvent {
    public val client: DeckClient
    public val gatewayId: Int
}

public interface EventService : WrappedEventSupplier {
    public val eventWrappingFlow: SharedFlow<DeckEvent>

    public fun startListening(): Job
}

public class DefaultEventService(private val client: DeckClient) : EventService {
    override val eventWrappingFlow: MutableSharedFlow<DeckEvent> = MutableSharedFlow()

    override val wrappedEventSupplierData: WrappedEventSupplierData = WrappedEventSupplierData(
        scope = client.gateway,
        sharedFlow = eventWrappingFlow
    )

    override fun startListening(): Job = client.gateway.on<GatewayEvent> {
        val deckEvent: DeckEvent = when (this) {
            is GatewayHelloEvent -> DeckHelloEvent.map(client, this)
            is GatewayServerXpAddedEvent -> DeckServerXpAddEvent.map(client, this)
            is GatewayChatMessageCreatedEvent -> DeckMessageCreateEvent.map(client, this)
            is GatewayChatMessageUpdatedEvent -> DeckMessageUpdateEvent.map(client, this)
            is GatewayChatMessageDeletedEvent -> DeckMessageDeleteEvent.map(client, this)
            is GatewayServerWebhookCreatedEvent -> DeckServerWebhookCreateEvent.map(client, this)
            is GatewayServerWebhookUpdatedEvent -> DeckServerWebhookUpdateEvent.map(client, this)
            is GatewayTeamMemberJoinedEvent -> DeckMemberJoinEvent.map(client, this)
            is GatewayTeamMemberRemovedEvent -> DeckMemberLeaveEvent.map(client, this)
            is GatewayTeamMemberBannedEvent -> DeckMemberBanEvent.map(client, this)
            is GatewayTeamMemberUnbannedEvent -> DeckMemberUnbanEvent.map(client, this)
            else -> return@on
        }
        eventWrappingFlow.emit(deckEvent)
    }
}

public interface EventMapper<F : GatewayEvent, T : DeckEvent> {
    public suspend fun map(client: DeckClient, event: F): T?
}