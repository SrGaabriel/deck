package com.deck.gateway.util

import com.deck.gateway.event.GatewayEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

// TODO: replace workaround
interface EventSupplier {
    val eventSupplierData: EventSupplierData
}

data class EventSupplierData(
    val scope: CoroutineScope,
    val sharedFlow: SharedFlow<GatewayEvent>,
    val listeningGatewayId: Int? = null
)

inline fun <reified T : GatewayEvent> EventSupplier.on(
    scope: CoroutineScope = eventSupplierData.scope,
    gatewayId: Int? = eventSupplierData.listeningGatewayId,
    noinline callback: suspend T.() -> Unit
): Job = on(gatewayId, scope, eventSupplierData.sharedFlow, callback)

suspend inline fun <reified T : GatewayEvent> EventSupplier.await(
    timeout: Long = 4000,
    scope: CoroutineScope = eventSupplierData.scope,
    gatewayId: Int? = eventSupplierData.listeningGatewayId
): T? = await(gatewayId, scope, eventSupplierData.sharedFlow, timeout)

inline fun <reified T : GatewayEvent> on(
    gatewayId: Int?,
    scope: CoroutineScope,
    eventsFlow: SharedFlow<GatewayEvent>,
    noinline callback: suspend T.() -> Unit
): Job = eventsFlow.buffer(Channel.UNLIMITED).filterIsInstance<T>().filter { it.gatewayId == (gatewayId ?: return@filter true) }.onEach(callback).launchIn(scope)

@OptIn(ExperimentalCoroutinesApi::class)
suspend inline fun <reified T : GatewayEvent> await(
    gatewayId: Int?,
    scope: CoroutineScope,
    eventsFlow: SharedFlow<GatewayEvent>,
    timeout: Long = 4000
): T? = withTimeoutOrNull(timeout) {
    suspendCancellableCoroutine<T> { continuation ->
        scope.launch {
            val event = eventsFlow.buffer(Channel.UNLIMITED).filterIsInstance<T>().filter { it.gatewayId == (gatewayId ?: return@filter true) }.first()
            continuation.resume(event) {}
        }
    }
}
