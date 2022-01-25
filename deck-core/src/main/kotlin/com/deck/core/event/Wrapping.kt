package com.deck.core.event

import com.deck.core.DeckClient
import com.deck.core.event.channel.DeckTeamChannelCreateEvent
import com.deck.core.event.channel.DeckTeamChannelDeleteEvent
import com.deck.core.event.channel.DeckTeamChannelUpdateEvent
import com.deck.core.event.channel.DeckTeamChannelsDeleteEvent
import com.deck.core.event.message.DeckMessageCreateEvent
import com.deck.core.util.WrappedEventSupplier
import com.deck.core.util.WrappedEventSupplierData
import com.deck.gateway.event.GatewayEvent
import com.deck.gateway.event.type.*
import com.deck.gateway.util.on
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

public interface DeckEvent {
    public val client: DeckClient
    public val gatewayId: Int
}

public interface EventService : WrappedEventSupplier {
    public val eventWrappingFlow: SharedFlow<DeckEvent>

    public fun startListeningAndConveying(): Job
}

public class DefaultEventService(private val client: DeckClient) : EventService {
    override val eventWrappingFlow: MutableSharedFlow<DeckEvent> = MutableSharedFlow()

    override val wrappedEventSupplierData: WrappedEventSupplierData = WrappedEventSupplierData(
        scope = client.gateway.orchestrator,
        sharedFlow = eventWrappingFlow
    )

    override fun startListeningAndConveying(): Job = client.gateway.orchestrator.on<GatewayEvent> {
        val deckEvent: DeckEvent = when (this) {
            is GatewayChatMessageCreatedEvent -> DeckMessageCreateEvent.map(client, this)
            is GatewayTeamChannelCreatedEvent -> DeckTeamChannelCreateEvent.map(client, this)
            is GatewayTeamChannelDeletedEvent -> DeckTeamChannelDeleteEvent.map(client, this)
            is GatewayTeamChannelUpdatedEvent -> DeckTeamChannelUpdateEvent.map(client, this)
            is GatewayTeamChannelsDeletedEvent -> DeckTeamChannelsDeleteEvent.map(client, this)
            else -> return@on
        } ?: return@on
        eventWrappingFlow.emit(deckEvent)
    }
}

public interface EventMapper<F : GatewayEvent, T : DeckEvent> {
    public suspend fun map(client: DeckClient, event: F): T?
}