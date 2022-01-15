package com.deck.core.util

import com.deck.common.util.DeckExperimental
import com.deck.core.event.DeckEvent
import com.deck.gateway.event.GatewayEvent
import com.deck.gateway.util.EventSupplier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow

interface WrappedEventSupplier {
    val wrappedEventSupplierData: WrappedEventSupplierData
}

data class WrappedEventSupplierData(
    val scope: CoroutineScope,
    val sharedFlow: SharedFlow<DeckEvent>,
    val listeningGatewayId: Int? = null
)

@DeckExperimental
inline fun <reified T : GatewayEvent> EventSupplier.on(
    scope: CoroutineScope = eventSupplierData.scope,
    gatewayId: Int? = eventSupplierData.listeningGatewayId,
    noinline callback: suspend T.() -> Unit
): Job = com.deck.gateway.util.on(gatewayId, scope, eventSupplierData.sharedFlow, callback)

@DeckExperimental
suspend inline fun <reified T : GatewayEvent> EventSupplier.await(
    timeout: Long = 4000,
    scope: CoroutineScope = eventSupplierData.scope,
    gatewayId: Int? = eventSupplierData.listeningGatewayId
): T? = com.deck.gateway.util.await(gatewayId, scope, eventSupplierData.sharedFlow, timeout)
