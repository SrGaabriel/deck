package io.github.srgaabriel.deck.gateway.util

import io.github.srgaabriel.deck.gateway.Gateway
import io.github.srgaabriel.deck.gateway.GatewayOrchestrator
import io.github.srgaabriel.deck.gateway.event.GatewayEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

public inline fun <reified T : GatewayEvent> Gateway.on(
    scope: CoroutineScope = this.scope,
    noinline callback: suspend T.() -> Unit
): Job = on(id, scope, eventFlow, callback)

public suspend inline fun <reified T : GatewayEvent> Gateway.await(
    timeout: Long,
    scope: CoroutineScope = this.scope,
): T? = await(id, scope, eventFlow, timeout)

public inline fun <reified T : GatewayEvent> GatewayOrchestrator.on(
    scope: CoroutineScope = this,
    gatewayId: Int? = null,
    noinline callback: suspend T.() -> Unit
): Job = on(gatewayId, scope, globalEventsFlow, callback)

public suspend inline fun <reified T : GatewayEvent> GatewayOrchestrator.await(
    timeout: Long,
    scope: CoroutineScope = this,
    gatewayId: Int? = null
): T? = await(gatewayId, scope, globalEventsFlow, timeout)

public inline fun <reified T : GatewayEvent> on(
    gatewayId: Int?,
    scope: CoroutineScope,
    eventsFlow: SharedFlow<GatewayEvent>,
    noinline callback: suspend T.() -> Unit
): Job = eventsFlow.buffer(Channel.UNLIMITED).filterIsInstance<T>()
    .filter { it.info.gatewayId == (gatewayId ?: return@filter true) }
    .onEachHandlingErrors(callback)
    .launchIn(scope)

@OptIn(ExperimentalCoroutinesApi::class)
public suspend inline fun <reified T : GatewayEvent> await(
    gatewayId: Int?,
    scope: CoroutineScope,
    eventsFlow: SharedFlow<GatewayEvent>,
    timeout: Long
): T? = withTimeoutOrNull(timeout) {
    suspendCancellableCoroutine<T> { continuation ->
        scope.launch {
            val event = eventsFlow.buffer(Channel.UNLIMITED).filterIsInstance<T>()
                .filter { it.info.gatewayId == (gatewayId ?: return@filter true) }.first()
            continuation.resume(event) {}
        }
    }
}

public inline fun <T> Flow<T>.onEachHandlingErrors(crossinline callback: suspend T.() -> Unit): Flow<T> =
    onEach {
        try {
            callback(it)
        } catch (exception: CancellationException) {
            throw exception
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }
    }