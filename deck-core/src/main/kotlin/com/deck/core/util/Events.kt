package com.deck.core.util

import com.deck.common.util.DeckDSL
import com.deck.common.util.DeckExperimental
import com.deck.core.event.DeckEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

interface WrappedEventSupplier {
    val wrappedEventSupplierData: WrappedEventSupplierData
}

data class WrappedEventSupplierData(
    val scope: CoroutineScope,
    val sharedFlow: SharedFlow<DeckEvent>,
    val listeningGatewayId: Int? = null
)

@DeckExperimental
inline fun <reified T : DeckEvent> WrappedEventSupplier.on(
    scope: CoroutineScope = wrappedEventSupplierData.scope,
    gatewayId: Int? = wrappedEventSupplierData.listeningGatewayId,
    noinline callback: suspend T.() -> Unit
): Job = on(gatewayId, scope, wrappedEventSupplierData.sharedFlow, callback)

@DeckExperimental
suspend inline fun <reified T : DeckEvent> WrappedEventSupplier.await(
    timeout: Long = 4000,
    scope: CoroutineScope = wrappedEventSupplierData.scope,
    gatewayId: Int? = wrappedEventSupplierData.listeningGatewayId
): T? = await(gatewayId, scope, wrappedEventSupplierData.sharedFlow, timeout)

// TODO: remove boilerplate
@DeckDSL
inline fun <reified T : DeckEvent> on(
    gatewayId: Int?,
    scope: CoroutineScope,
    eventsFlow: SharedFlow<DeckEvent>,
    noinline callback: suspend T.() -> Unit
): Job = eventsFlow.buffer(Channel.UNLIMITED).filterIsInstance<T>().filter { it.gatewayId == (gatewayId ?: return@filter true) }.onEach(callback).launchIn(scope)

@OptIn(ExperimentalCoroutinesApi::class)
suspend inline fun <reified T : DeckEvent> await(
    gatewayId: Int?,
    scope: CoroutineScope,
    eventsFlow: SharedFlow<DeckEvent>,
    timeout: Long = 4000
): T? = withTimeoutOrNull(timeout) {
    suspendCancellableCoroutine<T> { continuation ->
        scope.launch {
            val event = eventsFlow.buffer(Channel.UNLIMITED).filterIsInstance<T>().filter { it.gatewayId == (gatewayId ?: return@filter true) }.first()
            continuation.resume(event) {}
        }
    }
}
