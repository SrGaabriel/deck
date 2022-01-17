package com.deck.core.event

import com.deck.core.DeckClient
import com.deck.core.event.message.DeckMessageCreateEvent
import com.deck.core.util.WrappedEventSupplier
import com.deck.core.util.WrappedEventSupplierData
import com.deck.gateway.event.GatewayEvent
import com.deck.gateway.event.type.GatewayChatMessageCreatedEvent
import com.deck.gateway.util.on
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

interface DeckEvent {
    val client: DeckClient
    val gatewayId: Int
}

interface EventService: WrappedEventSupplier {
    val eventWrappingFlow: SharedFlow<DeckEvent>

    fun startListeningAndConveying(): Job
}

class DefaultEventService(private val client: DeckClient): EventService {
    override val eventWrappingFlow: MutableSharedFlow<DeckEvent> = MutableSharedFlow()

    override val wrappedEventSupplierData: WrappedEventSupplierData = WrappedEventSupplierData(
        scope = client.gateway.orchestrator,
        sharedFlow = eventWrappingFlow
    )

    override fun startListeningAndConveying() = client.gateway.orchestrator.on<GatewayEvent> {
        val deckEvent: DeckEvent = when (this) {
            is GatewayChatMessageCreatedEvent -> DeckMessageCreateEvent.map(client, this)
            else -> return@on
        }
        eventWrappingFlow.emit(deckEvent)
    }
}

interface EventMapper<F : GatewayEvent, T : DeckEvent> {
    fun map(client: DeckClient, event: F): T
}
