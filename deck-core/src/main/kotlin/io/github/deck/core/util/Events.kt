package io.github.deck.core.util

import io.github.deck.core.DeckClient
import io.github.deck.core.event.DeckEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

public inline fun <reified T : DeckEvent> DeckClient.on(
    scope: CoroutineScope = eventService,
    gatewayId: Int? = null,
    noinline callback: suspend T.() -> Unit
): Job = on(gatewayId, scope, eventService.eventWrappingFlow, callback)

public suspend inline fun <reified T : DeckEvent> DeckClient.await(
    timeout: Long = 4000,
    scope: CoroutineScope = eventService,
    gatewayId: Int? = null
): T? = await(gatewayId, scope, eventService.eventWrappingFlow, timeout)

// TODO: remove boilerplate
public inline fun <reified T : DeckEvent> on(
    gatewayId: Int?,
    scope: CoroutineScope,
    eventsFlow: SharedFlow<DeckEvent>,
    noinline callback: suspend T.() -> Unit
): Job = eventsFlow.buffer(Channel.UNLIMITED).filterIsInstance<T>()
    .filter { it.payload.gatewayId == (gatewayId ?: return@filter true) }.onEach(callback).launchIn(scope)

@OptIn(ExperimentalCoroutinesApi::class)
public suspend inline fun <reified T : DeckEvent> await(
    gatewayId: Int?,
    scope: CoroutineScope,
    eventsFlow: SharedFlow<DeckEvent>,
    timeout: Long = 4000
): T? = withTimeoutOrNull(timeout) {
    suspendCancellableCoroutine<T> { continuation ->
        scope.launch {
            val event = eventsFlow.buffer(Channel.UNLIMITED).filterIsInstance<T>()
                .filter { it.payload.gatewayId == (gatewayId ?: return@filter true) }.first()
            continuation.resume(event) {}
        }
    }
}