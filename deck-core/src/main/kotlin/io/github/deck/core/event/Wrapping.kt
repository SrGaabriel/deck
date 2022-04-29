package io.github.deck.core.event

import io.github.deck.core.DeckClient
import io.github.deck.core.event.message.MessageCreateEvent
import io.github.deck.core.event.message.MessageDeleteEvent
import io.github.deck.core.event.message.MessageUpdateEvent
import io.github.deck.core.event.server.*
import io.github.deck.core.event.user.HelloEvent
import io.github.deck.core.event.webhook.ServerWebhookCreateEvent
import io.github.deck.core.event.webhook.ServerWebhookUpdateEvent
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
            is GatewayHelloEvent -> HelloEvent.map(client, this)
            is GatewayServerXpAddedEvent -> ServerXpAddEvent.map(client, this)
            is GatewayChatMessageCreatedEvent -> MessageCreateEvent.map(client, this)
            is GatewayChatMessageUpdatedEvent -> MessageUpdateEvent.map(client, this)
            is GatewayChatMessageDeletedEvent -> MessageDeleteEvent.map(client, this)
            is GatewayServerWebhookCreatedEvent -> ServerWebhookCreateEvent.map(client, this)
            is GatewayServerWebhookUpdatedEvent -> ServerWebhookUpdateEvent.map(client, this)
            is GatewayTeamMemberJoinedEvent -> MemberJoinEvent.map(client, this)
            is GatewayTeamMemberRemovedEvent -> MemberLeaveEvent.map(client, this)
            is GatewayTeamMemberBannedEvent -> MemberBanEvent.map(client, this)
            is GatewayTeamMemberUnbannedEvent -> MemberUnbanEvent.map(client, this)
            else -> return@on
        }
        eventWrappingFlow.emit(deckEvent)
    }
}

public interface EventMapper<F : GatewayEvent, T : DeckEvent> {
    public suspend fun map(client: DeckClient, event: F): T?
}