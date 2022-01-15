package com.deck.core.event

import com.deck.core.module.GatewayModule
import com.deck.core.util.WrappedEventSupplier
import com.deck.core.util.WrappedEventSupplierData
import com.deck.gateway.event.GatewayEvent
import com.deck.gateway.util.on
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class DeckEvent(val gatewayId: Int)

interface EventService: WrappedEventSupplier {
    val eventWrappingFlow: SharedFlow<DeckEvent>

    fun startListeningAndConveying(): Job
}

class DefaultEventService(private val gateway: GatewayModule): EventService {
    override val eventWrappingFlow: MutableSharedFlow<DeckEvent> = MutableSharedFlow()

    override val wrappedEventSupplierData: WrappedEventSupplierData = WrappedEventSupplierData(
        scope = gateway.orchestrator,
        sharedFlow = eventWrappingFlow
    )

    override fun startListeningAndConveying() = gateway.orchestrator.on<GatewayEvent> {

    }
}
